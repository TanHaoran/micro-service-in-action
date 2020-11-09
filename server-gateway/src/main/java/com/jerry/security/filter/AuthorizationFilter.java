package com.jerry.security.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/9
 * Time: 22:08
 * Description:
 */
@Component
@Slf4j
public class AuthorizationFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("authorization start");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        // 当前请求是否需要身份认证
        if (isNeedAuth(request)) {
            TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
            if (tokenInfo != null && tokenInfo.isActive()) {
                // 是否具有权限
                if (!hasPermission(tokenInfo, request)) {
                    log.info("audit log update fail 403");
                    // 处理错误
                    handleError(403, requestContext);
                }
                requestContext.addZuulRequestHeader("username", tokenInfo.getUser_name());
            }
            // 获取 token 失败的情况
            else {
                // 如果是 token 的请求，则不会报错
                if (!StringUtils.startsWith(request.getRequestURI(), "/token")) {
                    log.info("audit log update fail 401");
                    // 处理错误
                    handleError(401, requestContext);
                }
            }
        }

        return null;
    }

    /**
     * 当前请求是否需要身份认证
     *
     * @param request
     * @return
     */
    private boolean isNeedAuth(HttpServletRequest request) {
        return true;
    }

    /**
     * 是否具有权限
     *
     * @param tokenInfo
     * @param request
     * @return
     */
    private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
        return RandomUtils.nextInt() % 2 == 0;
    }

    /**
     * 处理错误
     *
     * @param status
     * @param requestContext
     */
    private void handleError(int status, RequestContext requestContext) {
        requestContext.getResponse().setContentType("application/json");
        requestContext.setResponseStatusCode(status);
        requestContext.setResponseBody("{\"message\":\"auth fail\"}");
        // 告诉网关不要往下走了
        requestContext.setSendZuulResponse(false);
    }

}
