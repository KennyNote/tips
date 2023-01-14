package com.learning.notebook.tips.rocketmq.demo.transaction;

import com.alibaba.fastjson.JSON;
import com.learning.notebook.tips.rocketmq.entity.TransferRecord;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TransactionProducer implements InitializingBean {

    private static final TransactionMQProducer producer = new TransactionMQProducer("ProducerGroupName");

    @Resource
    private TransactionListenerImpl transactionListener;

    @Override
    public void afterPropertiesSet() {
        try {
            producer.setNamesrvAddr("192.168.3.51:9876");
            producer.setInstanceName("Producer");
            producer.setTransactionListener(transactionListener);
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }


    public void produce(Long money) {
        String businessNo = UUID.randomUUID().toString();

        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setFromUserId(1L);
        transferRecord.setToUserId(2L);
        transferRecord.setChangeMoney(money);
        transferRecord.setRecordNo(businessNo);

        try {
            Message msg = new Message("TransactionMessage", "tag", businessNo, JSON.toJSONString(transferRecord).getBytes(StandardCharsets.UTF_8));
            log.info("prepare事务消息发送开始。msg：" + new String(msg.getBody()));
            SendResult sendResult = producer.sendMessageInTransaction(msg, null);
            log.info("prepare事务消息发送结束，结果：" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
