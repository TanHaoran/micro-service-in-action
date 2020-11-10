package com.jerry.security.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/10
 * Time: 22:39
 * Description:
 */
@Component
public class MyRateLimitErrorHandler extends DefaultRateLimiterErrorHandler {

    @Override
    public void handleError(String msg, Exception e) {
        super.handleError(msg, e);
    }

}
