package com.jerry.security.user;

import lombok.Data;

import javax.persistence.Entity;
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
    private Long id;

    private String name;

}
