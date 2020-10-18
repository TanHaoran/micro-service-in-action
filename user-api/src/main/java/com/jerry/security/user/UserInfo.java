package com.jerry.security.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/18
 * Time: 13:40
 * Description:
 */
@Data
public class UserInfo {

    private Long id;

    private String name;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

}
