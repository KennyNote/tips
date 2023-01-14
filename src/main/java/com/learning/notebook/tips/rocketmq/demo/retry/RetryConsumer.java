package com.learning.notebook.tips.rocketmq.demo.retry;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class RetryConsumer {

    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroupName");
        consumer.setNamesrvAddr("192.168.3.51:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("someTopic", "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            try {
                // ....
            } catch (Throwable e) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // 返回消费状态：消费成功
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 开启消费者消费
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
