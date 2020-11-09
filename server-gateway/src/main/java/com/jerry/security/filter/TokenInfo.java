package com.jerry.security.filter;

import lombok.Data;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/9
 * Time: 21:52
 * Description:
 */
@Data
public class TokenInfo {

    /**
     * 令牌知否可用
     */
    private boolean active;

    /**
     * 客户端id
     */
    private String client_id;

    /**
     * 令牌范围
     */
    private String[] scope;

    /**
     * 用户
     */
    private String user_name;

    /**
     * 令牌可以访问哪些资源服务器
     */
    private String[] aud;

    /**
     * 过期时间
     */
    private Date exp;

    /**
     * 令牌对应用户所拥有的的权限
     */
    private String[] authorities;

}
