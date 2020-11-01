package com.jerry.security.config;

import com.jerry.security.filter.AclInterceptor;
import com.jerry.security.filter.AuditLogInterceptor;
import com.jerry.security.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-10-22
 * Time: 10:26
 * Description:
 */
@Configuration
@EnableJpaAuditing
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuditLogInterceptor auditLogInterceptor;

    @Autowired
    private AclInterceptor aclInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这些 Interceptor 会根据下面 addInterceptor() 的顺序来先后执行
        // 在 addInterceptor() 方法后面追加 addPathPatterns() 可以指定针对某些链接生效，如果不指定就全部生效
        registry.addInterceptor(auditLogInterceptor);
        registry.addInterceptor(aclInterceptor);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            ServletRequestAttributes servletRequestAttributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            UserInfo info = (UserInfo) servletRequestAttributes.getRequest().getSession().getAttribute("user");
            String username = null;
            if (info != null) {
                username = info.getUsername();
            }
            return Optional.ofNullable(username);
        };
    }

}
