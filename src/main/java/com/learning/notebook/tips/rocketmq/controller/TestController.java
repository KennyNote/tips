package com.learning.notebook.tips.rocketmq.controller;

import com.learning.notebook.tips.rocketmq.demo.transaction.TransactionProducer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chenyin
 * @since 2019-05-10
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Resource
    private TransactionProducer transactionProducer;

    @GetMapping("/mqTest/{data}")
    public String callback(@PathVariable("data") String data) {
        transactionProducer.produce(Long.parseLong(data));
        return "Ok";
    }

}
