package com.learning.notebook.tips.mvc.controller;

import com.learning.notebook.tips.common.annotation.RequestAuth;
import com.learning.notebook.tips.common.annotation.RequestLog;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {

    @GetMapping("test1")
    public String test1() {
        Thread thread = Thread.currentThread();
        CompletableFuture.runAsync(() -> {
            log.info("thread.getName():{}", thread.getName());
            log.info("this.hashCode():{}", this.hashCode());
        });

        return "success";
    }

    @GetMapping("test2")
    @RequestLog
    @RequestAuth
    public String test2() throws ExecutionException, InterruptedException {
        Thread thread = Thread.currentThread();
        return CompletableFuture.supplyAsync(() -> {
            log.info("thread.getName():{}", thread.getName());
            log.info("this.hashCode():{}", this.hashCode());
            return thread.getName();
        }).get();
    }
}
