package com.jerry.security.admin;

import lombok.Data;

import java.time.LocalDateTime;

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

    private String refresh_token;

    private String token_type;

    private Long expires_in;

    private String scope;

    private LocalDateTime expireTime;

    public TokenInfo init() {
        // 当前时间 + 过期的秒数
        expireTime = LocalDateTime.now().plusSeconds(expires_in - 3);
        return this;
    }

    /**
     * 判断令牌是否过期
     *
     * @return
     */
    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
