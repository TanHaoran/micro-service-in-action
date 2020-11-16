package com.jerry.security.admin;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

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
            requestContext.addZuulRequestHeader("Authorization", "bearer " + token.getAccess_token());
        }

        return null;
    }

}
