package com.jerry.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/9
 * Time: 22:07
 * Description:
 */
@Component
@Slf4j
public class AuditLogFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("audit log insert");
        return null;
    }

}
