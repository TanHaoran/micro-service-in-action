package com.jerry.security.log;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-10-22
 * Time: 10:06
 * Description:
 */
public interface AuditLogRepository extends JpaSpecificationExecutor<AuditLog>, CrudRepository<AuditLog, Long> {
}
