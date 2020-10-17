package com.jerry.security.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/17
 * Time: 15:11
 * Description:
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

    List<User> findByName(String name);

}
