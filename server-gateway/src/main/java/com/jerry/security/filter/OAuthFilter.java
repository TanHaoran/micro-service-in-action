package com.jerry.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/9
 * Time: 21:41
 * Description:
 */
@Slf4j
@Component
public class OAuthFilter extends ZuulFilter {

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * 从 pre post error route 中选择一个。
     * route 是用来控制路由的，zuul 一般帮助做好了
     * pre 业务逻辑之前执行 run() 方法中的逻辑
     * post 业务逻辑之后执行 run() 方法中的逻辑
     * error 业务逻辑抛出异常后会执行 run() 方法中的逻辑
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序，越小优先级越高
     *
     * @return
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 过滤器是否执行
     *
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的业务逻辑
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        log.info("oauth start");
        RequestContext requestContext = RequestContext.getCurrentContext();
        // 获取当前请求
        HttpServletRequest request = requestContext.getRequest();
        if (StringUtils.startsWith(request.getRequestURI(), "/token")) {
            return null;
        }
        // 其它请求即业务逻辑请求
        String authHeader = request.getHeader("Authorization");
        // 如果没有携带 Authorization 的请求头
        if (StringUtils.isBlank(authHeader)) {
            return null;
        }

        // 如果是 OAuth2 bearer 类型的认证
        if (!StringUtils.startsWithIgnoreCase(authHeader, "bearer ")) {
            return null;
        }

        try {
            TokenInfo info = getTokenInfo(authHeader);
            request.setAttribute("tokenInfo", info);
        } catch (Exception e) {
            log.error("get tokenInfo fail", e);
        }
        return null;
    }

    private TokenInfo getTokenInfo(String authHeader) {
        String token = StringUtils.substringAfter(authHeader, "bearer ");
        String oAuthServiceUrl = "http://localhost:9090/oauth/check_token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("gateway", "123456");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", token);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        // 发送请求
        ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oAuthServiceUrl, HttpMethod.POST, entity,
                TokenInfo.class);
        log.info("tokenInfo: {}", responseEntity.getBody().toString());

        return responseEntity.getBody();
    }

}
