package com.jerry.security.admin;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-11-16
 * Time: 10:56
 * Description:
 */
@Component
public class SessionTokenFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获取当前请求
        HttpServletRequest request = requestContext.getRequest();

        // 拼凑请求头
        TokenInfo token = (TokenInfo) request.getSession().getAttribute("token");

        if (token != null) {
            String accessToken = token.getAccess_token();
            // 判断令牌是否过期
            if (token.isExpired()) {
                // 刷新令牌
                String oAuthServiceUrl = "http://localhost:9070/token/oauth/token";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("admin", "123456");

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", token.getRefresh_token());

                HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);

                try {
                    // 发送请求
                    ResponseEntity<TokenInfo> newToken = restTemplate.exchange(oAuthServiceUrl, HttpMethod.POST, entity,
                        TokenInfo.class);

                    request.getSession().setAttribute("token", newToken.getBody().init());
                    accessToken = newToken.getBody().getAccess_token();
                } catch (Exception e) {
                    // 请求到此截止
                    requestContext.setSendZuulResponse(false);
                    requestContext.setResponseStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    requestContext.setResponseBody("{\"message\":\"refresh fail\"}");
                    requestContext.getResponse().setContentType("application/json");
                }
            }
            requestContext.addZuulRequestHeader("Authorization", "bearer " + accessToken);
        }

        return null;
    }

}
