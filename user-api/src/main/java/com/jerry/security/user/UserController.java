package com.jerry.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/17
 * Time: 14:28
 * Description:
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping
    public UserInfo create(@RequestBody @Validated UserInfo user) {
        return userService.create(user);
    }

    @PutMapping("/{id}")
    public UserInfo update(@RequestBody UserInfo user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("{id}")
    public UserInfo get(@PathVariable Long id, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        if (user == null || !user.getId().equals(id)) {
            throw new RuntimeException("身份认证信息异常，获取用户信息失败");
        }
        return userService.get(id);
    }

    @GetMapping("/query")
    public List<UserInfo> query(String name) {
        return userService.query(name);
    }

    @GetMapping("/queryJdbc")
    public List<Map<String, Object>> queryJdbc(String name) {
        String sql = "SELECT id, name FROM user WHERE name = '" + name + "'";
        List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
        return data;
    }

}
