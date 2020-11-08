package com.jerry.security.server.resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/11/8
 * Time: 21:05
 * Description:
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * 生产中具体的获取用户信息的逻辑就可以在这个方法中写
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();
        user.setId(1L);
        user.setUsername(username);
        return user;
    }

}
