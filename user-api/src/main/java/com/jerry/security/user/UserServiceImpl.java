package com.jerry.security.user;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/18
 * Time: 13:45
 * Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserInfo create(UserInfo info) {
        User user = new User();
        BeanUtils.copyProperties(info, user);
        userRepository.save(user);
        info.setId(user.getId());
        return info;
    }

    @Override
    public UserInfo update(UserInfo user) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public UserInfo get(Long id) {
        return userRepository.findById(id).get().buildInfo();
    }

    @Override
    public List<UserInfo> query(String name) {
        return null;
    }

}
