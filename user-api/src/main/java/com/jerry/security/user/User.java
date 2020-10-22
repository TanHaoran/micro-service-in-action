package com.jerry.security.user;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/17
 * Time: 14:11
 * Description:
 */
@Entity
@Data
public class User {

    @Id
    // 使用数据库主键的策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "username不能为空")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "password不能为空")
    private String password;

    private String permissions;

    public UserInfo buildInfo() {
        UserInfo info = new UserInfo();
        BeanUtils.copyProperties(this, info);
        return info;
    }

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
