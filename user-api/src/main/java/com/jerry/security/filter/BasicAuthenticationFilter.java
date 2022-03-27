package com.jerry.security.filter;

import com.jerry.security.user.User;
import com.jerry.security.user.UserRepository;
import com.lambdaworks.crypto.SCryptUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/18
 * Time: 14:09
 * Description:
 */
@Component
@Order(2)
public class BasicAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println(2);
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(authHeader)) {
            // 从请求头中解析出来加密的认证字符串
            String token64 = StringUtils.substringAfter(authHeader, "Basic ");
            String token = new String(Base64Utils.decodeFromString(token64));
            String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(token, ":");

            String username = items[0];
            String password = items[1];

            User user = userRepository.findByUsername(username);
            // 匹配密码是否正确
            if (user != null && SCryptUtil.check(password, user.getPassword())) {
                request.getSession().setAttribute("user", user.buildInfo());
                request.getSession().setAttribute("temp", "yes");
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // 这里判断如果是通过 Basic 方式登录的请求，则每次都会销毁 session，从而使得每次 Basic 方式登录都需要用户名密码
            HttpSession session = request.getSession();
            if (session.getAttribute("temp") != null) {
                session.invalidate();
            }
        }

    }
}
