package com.jerry.security.config;

import com.jerry.security.filter.AuditLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这些 Interceptor 会根据下面 addInterceptor() 的顺序来先后执行
        // 在 addInterceptor() 方法后面追加 addPathPatterns() 可以指定针对某些链接生效，如果不指定就全部生效
        registry.addInterceptor(auditLogInterceptor);
    }

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of("auditorAware-jerry");
    }

}
