package com.jerry.security.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/18
 * Time: 13:45
 * Description:
 */
public interface UserService {

    UserInfo create(@RequestBody UserInfo user);

    UserInfo update(@RequestBody UserInfo user);

    void delete(@PathVariable Long id);

    UserInfo get(@PathVariable Long id);

    List<UserInfo> query(String name);

    UserInfo login(UserInfo user);

}
