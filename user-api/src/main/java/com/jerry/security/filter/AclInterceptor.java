package com.jerry.security.filter;

import com.jerry.security.user.User;
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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(4);
        boolean result = true;
        User user = (User) request.getAttribute("user");
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

        return result;
    }
}
