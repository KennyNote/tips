package com.learning.notebook.tips.rocketmq.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class User implements Serializable {
    private Long id;

    /**
     * 余额
     */
    private Long money;

    /**
     * name
     */
    private String name;

    /**
     * password
     */
    private String password;

    /**
     * create_time
     */
    private LocalDateTime createTime;

    /**
     * update_time
     */
    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}