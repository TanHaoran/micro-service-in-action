package com.jerry.security.user;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private String username;

    private String password;

}
