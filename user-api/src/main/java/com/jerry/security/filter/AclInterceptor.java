package com.jerry.security.filter;

import com.jerry.security.user.User;
import com.jerry.security.user.UserInfo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/22
 * Time: 20:23
 * Description:
 */
@Component
@Order(4)
public class AclInterceptor extends HandlerInterceptorAdapter {

    private String[] permitUrls = new String[]{"/users/login"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(4);
        boolean result = true;

        // 如果是非允许的请求，则都必须走授权逻辑
        if (!ArrayUtils.contains(permitUrls, request.getRequestURI())) {
            UserInfo user = (UserInfo) request.getSession().getAttribute("user");
            // 如果拿不到用户信息
            if (user == null) {
                response.setContentType("text/plain");
                response.getWriter().write("Need authentication");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                result = false;
            }
            // 如果可以拿到用户信息
            else {
                String method = request.getMethod();
                if (!user.hasPermission(method)) {
                    response.setContentType("text/plain");
                    response.getWriter().write("forbidden");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    result = false;
                }
            }
        }

        return result;
    }
}
