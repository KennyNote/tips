package com.learning.notebook.tips.db.mapper;

import com.learning.notebook.tips.rocketmq.entity.TransferRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * TransferRecordDAO继承基类
 */
@Mapper
@Repository
public interface TransferRecordMapper extends BaseMapper<TransferRecord, Long> {

    int selectCount(@Param("transactionId") String transactionId);
}