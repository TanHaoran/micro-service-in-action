package com.jerry.security.filter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/17
 * Time: 16:52
 * Description:
 */
@Component
public class ReteLimitFilter extends OncePerRequestFilter {

    /**
     * 配置每秒允许 1 个请求
     */
    private RateLimiter rateLimiter = RateLimiter.create(1);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 判断是否达到限流器的限制
        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(request, response);
        }
        // 达到限制，则返回 http 状态码 429
        else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.getWriter().write("Too many request!");
            response.getWriter().flush();
        }
    }

}
