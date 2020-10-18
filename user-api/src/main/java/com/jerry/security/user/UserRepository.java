package com.jerry.security.user;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020/10/17
 * Time: 15:11
 * Description:
 */
public interface UserRepository extends JpaSpecificationExecutor<User>, CrudRepository<User, Long> {

    User findByUsername(String username);

}
