package com.learning.notebook.tips.rocketmq.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * @author 
 * 
 */
@Data
public class TransferRecord implements Serializable {
    private Long id;

    /**
     * 转账人id
     */
    private Long fromUserId;

    /**
     * 转账金额
     */
    private Long changeMoney;

    /**
     * 消息事务id
     */
    private String transactionId;

    /**
     * 被转账人id
     */
    private Long toUserId;

    /**
     * 转账流水编号
     */
    private String recordNo;

    private static final long serialVersionUID = 1L;
}