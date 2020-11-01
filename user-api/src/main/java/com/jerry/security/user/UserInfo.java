package com.jerry.security.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

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

    private String permissions;

    public boolean hasPermission(String method) {
        boolean result;
        if (StringUtils.equalsIgnoreCase("get", method)) {
            result = StringUtils.contains(permissions, "r");
        } else {
            result = StringUtils.contains(permissions, "w");
        }
        return result;
    }
}
