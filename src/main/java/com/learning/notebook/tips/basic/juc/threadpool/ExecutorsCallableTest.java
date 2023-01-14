package com.learning.notebook.tips.basic.juc.threadpool;

import com.learning.notebook.tips.basic.exception.DIYException.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorsCallableTest {

    public static void main(String[] args) {

        List<Future<Integer>> validateFutures = new ArrayList<>(5);

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            validateFutures.add(fixedThreadPool.submit(() -> doNothing(finalI)));
        }

        for (int i = 0; i < 5; i++) {
            try {
                Integer integer = validateFutures.get(i).get();
                System.out.println(integer);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println(e.getMessage());
            } catch (BusinessException | ExecutionException businessException) {
                System.out.println(businessException.getMessage());
                // 这里看业务情况看是否需要break，如果说其中一个任务发生异常就停止get，那么需要break掉循环。
                break;
            }
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
            throw new BusinessException("do exception");
        }
        return i;
    }
}