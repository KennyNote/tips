package com.learning.notebook.tips.rocketmq.demo.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * This example shows how to subscribe and consume messages using providing {@link DefaultMQPushConsumer}.
 */
@Slf4j
public class Consumer {

    public static void main(String[] args) throws InterruptedException, MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
        consumer.setNamesrvAddr("192.168.3.51:9876");
        consumer.setInstanceName("Consumer");
        consumer.subscribe("TransactionMessage", "tag");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
            for (MessageExt msg : list) {
                log.info("收到消息," + new String(msg.getBody()));
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        log.info("Consumer Started.%n");
    }
}
