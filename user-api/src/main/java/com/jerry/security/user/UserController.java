package com.jerry.security.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @GetMapping("/login")
    public void login(@Validated UserInfo user, HttpServletRequest request) {
        UserInfo info = userService.login(user);
        // 这里的 false 表示，如果获取不到 session 的时候不会创建一个新的 session，就会返回一个 null，这是为了防止 session 攻击
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        request.getSession(true).setAttribute("user", info);
    }

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
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
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
