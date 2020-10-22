package com.jerry.security.log;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * User: Jerry
 * Date: 2020-10-20
 * Time: 10:31
 * Description:
 */
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    private String path;

    private Integer status;

    @CreatedBy
    private String username;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date modifyTime;

}
