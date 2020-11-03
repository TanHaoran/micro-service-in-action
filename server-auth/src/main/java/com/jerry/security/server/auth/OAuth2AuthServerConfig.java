package com.jerry.security.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-11-03
 * Time: 13:55
 * Description:
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 配置客户端的信息
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端应用名
                .withClient("orderApp")
                // 密码
                .secret(passwordEncoder.encode("123456"))
                // 权限
                .scopes("read", "write")
                // 令牌的有效期，单位秒
                .accessTokenValiditySeconds(3600)
                // 资源服务器id，表示这个令牌可以访问哪些资源服务器
                .resourceIds("order-server")
                // 授权方式
                .authorizedGrantTypes("password")
                .and()
                .withClient("orderService")
                .secret(passwordEncoder.encode("123456"))
                .scopes("read")
                .accessTokenValiditySeconds(3600)
                .resourceIds("order-server")
                .authorizedGrantTypes("password");
    }

}
