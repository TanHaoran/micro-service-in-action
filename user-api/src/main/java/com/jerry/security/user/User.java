package com.jerry.security.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    private String username;

    private String password;

}
