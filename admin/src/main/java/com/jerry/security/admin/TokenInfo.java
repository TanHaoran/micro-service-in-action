package com.jerry.security.admin;

import lombok.Data;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/12
 * Time: 22:52
 * Description:
 */
@Data
public class TokenInfo {

    private String access_token;

    private String token_type;

    private String expires_in;

    private String scope;

}
