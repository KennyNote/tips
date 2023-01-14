package com.learning.notebook.tips.basic.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

//CachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
public class ExecutorsTest {

    public static void main(String[] args) {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        IntStream.range(0,5).forEach(index -> cachedThreadPool.execute(ExecutorsTest::doNothing));

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        IntStream.range(0,5).forEach(index -> fixedThreadPool.execute(ExecutorsTest::doNothing));

        Executors.newSingleThreadExecutor().execute(ExecutorsTest::doNothing);

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(4);
        scheduledThreadPool.scheduleAtFixedRate(ExecutorsTest::doNothing, 1000, 1000, TimeUnit.MILLISECONDS);
        scheduledThreadPool.scheduleAtFixedRate(ExecutorsTest::doNothing, 1000, 1000, TimeUnit.MILLISECONDS);

    }

    public static void doNothing(){
    }
}