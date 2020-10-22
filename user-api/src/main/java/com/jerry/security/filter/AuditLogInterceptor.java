package com.jerry.security.filter;

import com.jerry.security.log.AuditLog;
import com.jerry.security.log.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-10-22
 * Time: 10:08
 * Description:
 */
@Component
public class AuditLogInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        AuditLog log = new AuditLog();
        log.setMethod(request.getMethod());
        log.setPath(request.getRequestURI());
        auditLogRepository.save(log);
        request.setAttribute("auditLogId", log.getId());
        // 如果这里返回 false 表示拒绝这个请求
        return true;
    }

    /**
     * afterCompletion() 方法无论是否成功都会执行，而 postHandle() 只有成功才会执行
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long auditLogId = (Long) request.getAttribute("auditLogId");
        AuditLog log = auditLogRepository.findById(auditLogId).get();
        log.setStatus(response.getStatus());
        auditLogRepository.save(log);
    }

}
