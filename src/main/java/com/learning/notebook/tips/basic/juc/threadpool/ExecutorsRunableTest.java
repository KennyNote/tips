package com.learning.notebook.tips.basic.juc.threadpool;

import com.learning.notebook.tips.basic.exception.DIYException.BusinessException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ExecutorsRunableTest {

    public static void main(String[] args) {

        AtomicBoolean validate = new AtomicBoolean(true);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            fixedThreadPool.execute(() -> {
                try {
                    doNothing(finalI);
                } catch (BusinessException e) {
                    System.out.println("hello");
                    validate.set(false);
                }
            });
        }
        if (validate.get() == false) {
            System.out.println("qwe");
        }
        System.out.println("end");
        fixedThreadPool.shutdown();

    }

    public static Integer doNothing(Integer i) {
        try {
            Thread.sleep(i * 1000);
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            interruptedException.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " # " + i);
        if (i == 2) {
            throw new BusinessException("xxxx");
        }
        return i;
    }
}