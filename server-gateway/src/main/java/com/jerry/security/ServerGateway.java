package com.jerry.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/9
 * Time: 21:21
 * Description:
 */
@SpringBootApplication
@EnableZuulProxy
public class ServerGateway {

    public static void main(String[] args) {
        SpringApplication.run(ServerGateway.class, args);
    }

}
