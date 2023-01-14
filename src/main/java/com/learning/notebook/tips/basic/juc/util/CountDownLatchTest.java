package com.learning.notebook.tips.basic.juc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchTest {

    /**
     * 可以实现类似计数器的功能。比如有一个任务A，它要等待其他4个任务执行完毕之后才能执行，此时就可以利用CountDownLatch来实现这种功能了。
     */
    public static void main(String[] args) {
        try {
            threadTest1();
            //            threadTest2();
            //            threadTest3();
            //            threadTest4();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 多个不同的线程干相同的事情，无返回值。
    public static void threadTest1() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(5);
        AtomicInteger ai = new AtomicInteger(0);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            exec.execute(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + ai.incrementAndGet());
                cdl.countDown();
            });
        }
        cdl.await();
        // 当count为0时被阻塞的线程才会继续执行；如果把cdl.await();放在输出后不一定输出5。
        System.out.println(ai.get());
        exec.shutdown();
    }

    // 多个不同的线程干相同的事情，有返回值。
    public static void threadTest3() throws InterruptedException, ExecutionException {
        List<Integer> results = new ArrayList<>();
        CountDownLatch cdl = new CountDownLatch(5);
        AtomicInteger ai = new AtomicInteger(0);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            Future<Integer> result = exec.submit(() -> {
                System.out.println(Thread.currentThread().getName() + ":" + ai.incrementAndGet());
                cdl.countDown();
                return ai.get();
            });
            results.add(result.get());
        }
        cdl.await();
        System.out.println(results.get(results.size() - 1));
        exec.shutdown();
    }

    // 多个不同的线程干不同的事情，无返回值。
    public static void threadTest2() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(2);
        AtomicInteger ai = new AtomicInteger(0);
        AtomicInteger bi = new AtomicInteger(100);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        exec.execute(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + ai.incrementAndGet());
            cdl.countDown();
        });
        exec.execute(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + bi.decrementAndGet());
            cdl.countDown();
        });

        cdl.await();
        System.out.println(ai.get());
        System.out.println(bi.get());
        exec.shutdown();
    }

    // 多个不同的线程干不同的事情，无返回值。
    public static void threadTest4() throws InterruptedException, ExecutionException {
        CountDownLatch cdl = new CountDownLatch(2);
        AtomicInteger ai = new AtomicInteger(0);
        AtomicInteger bi = new AtomicInteger(100);
        ExecutorService exec = Executors.newFixedThreadPool(5);
        Future<Integer> result1 = exec.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + ai.incrementAndGet());
            cdl.countDown();
            return ai.get();
        });
        Future<Integer> result2 = exec.submit(() -> {
            System.out.println(Thread.currentThread().getName() + ":" + bi.decrementAndGet());
            cdl.countDown();
            return bi.get();
        });

        cdl.await();
        System.out.println(result1.get());
        System.out.println(result2.get());
        exec.shutdown();
    }

}
