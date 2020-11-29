package com.jerry.security.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/12
 * Time: 22:41
 * Description:
 */
@SpringBootApplication
@RestController
@EnableZuulProxy
@Slf4j
public class AdminApplication {

    private RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/login")
    public void login(@RequestBody Credentials credentials, HttpServletRequest request) {
        String oAuthServiceUrl = "http://localhost:9070/token/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", credentials.getUsername());
        params.add("password", credentials.getPassword());
        params.add("grant_type", "password");
        params.add("scope", "read write");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        // 发送请求
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oAuthServiceUrl, HttpMethod.POST, entity,
            TokenInfo.class);

        request.getSession().setAttribute("token", responseEntity.getBody());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        // 将 session 失效
        request.getSession().invalidate();
    }

    @GetMapping("/oauth/callback")
    public void callback(@RequestParam String code, String state, HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        log.info("state is {}", state);

        String oAuthServiceUrl = "http://localhost:9070/token/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("admin", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("grant_type", "authorization_code");
        params.add("redirect_uri", "http://admin.com:8080/oauth/callback");

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        // 发送请求
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oAuthServiceUrl, HttpMethod.POST, entity,
            TokenInfo.class);

        request.getSession().setAttribute("token", responseEntity.getBody());

        response.sendRedirect("/");
    }

    @GetMapping("/me")
    public TokenInfo me(HttpServletRequest request) {
        return (TokenInfo) request.getSession().getAttribute("token");
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
