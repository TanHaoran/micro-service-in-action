package com.jerry.security.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/12
 * Time: 22:41
 * Description:
 */
@SpringBootApplication
@RestController
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

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
