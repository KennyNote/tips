package com.learning.notebook.tips.rocketmq.demo.filter;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

public class FilterBySQLProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("192.168.3.51:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            try {
                byte[] body = ("Hi," + i).getBytes();
                Message msg = new Message("TopicE", "myTag", body);
                // 事先埋入用户属性age
                msg.putUserProperty("age", i + "");
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        producer.shutdown();
    }
}
