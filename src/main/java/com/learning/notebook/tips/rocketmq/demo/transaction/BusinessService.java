package com.learning.notebook.tips.rocketmq.demo.transaction;

import com.learning.notebook.tips.db.mapper.TransferRecordMapper;
import com.learning.notebook.tips.db.mapper.UserMapper;
import com.learning.notebook.tips.rocketmq.entity.TransferRecord;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: chenyin
 * @date: 2019-05-10 17:37
 */
@Slf4j
@Service
public class BusinessService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TransferRecordMapper transferRecordMapper;

    /**
     * 转账操作 A扣钱，同时新增转账明细
     *
     * @param fromUserId 转账人id
     * @param toUserId 被转账人id
     * @param changeMoney 转账金额
     * @param businessNo 单次转账唯一业务标识
     * @param transactionId 事务消息事务id
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean doTransfer(Long fromUserId, Long toUserId, Long changeMoney, String businessNo, String transactionId) {
        //插入转账记录明细 businessNo加唯一建 做去重操作 防止消息重试发送 导致本地事务多次执行 重复扣钱
        //转账记录中 记录 消息事务transactionId 用于后续状态回查
        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setFromUserId(fromUserId);
        transferRecord.setChangeMoney(changeMoney);
        transferRecord.setTransactionId(transactionId);
        transferRecord.setToUserId(toUserId);
        transferRecord.setRecordNo(businessNo);

        transferRecordMapper.insert(transferRecord);

        //执行A扣钱操作
        //update user set money = money - #{money} where id = #{userId} and money >= #{money}
        int result = userMapper.reduceMoney(fromUserId, changeMoney);
        if (result <= 0) {
            throw new RuntimeException("账户余额不足");
        }
        log.info("转账成功,fromUserId:" + fromUserId + ",toUserId:" + toUserId + ",money:" + changeMoney);
        return true;
    }

    /**
     * 检查本地扣钱事务执行状态
     */
    public boolean checkTransferStatus(String transactionId) {
        //根据transactionId查询转账记录 有转账记录 标识本地事务执行成功 即A扣钱成功
        log.info("检查本地扣钱事务执行状态");
        int count = transferRecordMapper.selectCount(transactionId);
        return count > 0;

    }

}
