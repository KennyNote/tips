package com.learning.notebook.tips.basic.juc.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {

    /**
     * 信号量, 用来表示 "可用资源的个数"，本质上就是一个计数器。该类用于控制信号量的个数，构造时传入个数。总数就是控制并发的数量。
     * 假如是5，程序执行前用acquire()方法获得信号，则可用信号变为4，程序执行完通过release()方法归还信号量，可用信号又变为5。
     * 如果可用信号为0，acquire就会造成阻塞，等待release释放信号。acquire和release方法可以不在同一个线程使用。
     */

    public static void main(String[] args) {
        // Semaphore(信号量)，用于做限流处理，比如说同时只允许5五个人访问，超过五个人访问就需要等待，类似这样的需求，下面的案例可以看出执行是五个五个的执行，等上一个五个执行完了，才会执行下一个。
        // 线程池
        ExecutorService exec = Executors.newCachedThreadPool();
        // 只能5个线程同时访问
        Semaphore semaphore = new Semaphore(5);
        // 模拟20个客户端访问
        for (int index = 0; index < 20; index++) {
            final int NO = index;
            exec.execute(() -> {
                try {
                    // 获取许可
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " Accessing: " + NO);
                    // 模拟实际业务逻辑，执行10秒业务。
                    Thread.sleep(10000);
                    // 使用完后释放
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            });
        }

        //System.out.println(semaphore.getQueueLength());
        // 退出线程池
        exec.shutdown();
    }

}
