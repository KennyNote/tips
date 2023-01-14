package com.learning.notebook.tips.basic.juc.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {

    /**
     * 1. 线程池刚创建时，里面没有一个线程。任务队列是作为参数传进来的。不过，就算队列里面有任务，线程池也不会马上执行它们。
     * 2. 当调用 execute() 方法添加一个任务时，线程池会做如下判断：
     * a) 如果正在运行的线程数量小于 corePoolSize，那么马上创建线程运行这个任务；
     * b) 如果正在运行的线程数量大于或等于 corePoolSize，那么将这个任务放入队列；
     * c) 如果这时候队列满了，而且正在运行的线程数量小于 maximumPoolSize，那么还是要创建非核心线程立刻运行这个任务；
     * d) 如果队列满了，而且正在运行的线程数量大于或等于 maximumPoolSize，那么线程池会抛出异常RejectExecutionException。
     * 3. 当一个线程完成任务时，它会从队列中取下一个任务来执行。
     * 4. 当一个线程无事可做，超过一定的时间（keepAliveTime）时，线程池会判断，如果当前运行的线程数大于 corePoolSize，那么这个线程就被停掉。所以线程池的所有任务完成后，它最终会收缩到 corePoolSize 的大小。
     */
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger ai = new AtomicInteger(0);

        int corePoolSize = 4;
        int maximumPoolSize = 8;
        long keepAliveTime = 10;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(2); // 阻塞队列，存放暂时无法执行的任务，等待执行！
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        RejectedExecutionHandlerImpl rejectedExecutionHandler = new RejectedExecutionHandlerImpl();// 拒绝任务有两种情况：1. 线程池已经被关闭；2. 任务队列已满且maximumPoolSizes已满；

        ThreadPoolExecutor executorPool = new ThreadPoolExecutor(
            corePoolSize, // 线程数量（指定了线程池中的线程数量，它的数量决定了添加的任务是开辟新的线程去执行，还是放到workQueue任务队列中去；）
            maximumPoolSize, // 线程池中最大线程数量（指定了线程池中的最大线程数量，这个参数会根据你使用的workQueue任务队列的类型，决定线程池会开辟的最大线程数量；）
            keepAliveTime, // 当线程池中空闲线程数量超过corePoolSize时，多余的线程会在多长时间内被销毁；
            unit,// keepAliveTime的单位
            workQueue,// 任务队列，被添加到线程池中，但尚未被执行的任务；它一般分为直接提交队列、有界任务队列、无界任务队列、优先任务队列几种；
            threadFactory,// 线程工厂，用于创建线程，一般用默认即可；
            rejectedExecutionHandler// 当任务太多来不及处理时，如何拒绝任务；
        );
        executorPool.prestartAllCoreThreads(); // 预启动所有核心线程

        ThreadMonitor monitor = new ThreadMonitor(executorPool, 1);
        monitor.start();
        Thread.sleep(1000);
        // 最好是 任务总数 = 队列大小 + 最大线程数，否则调用RejectedExecutionHandler的rejectedExecution方法
        for (int i = 0; i < 15; i++) {
            int finalI = i;
            executorPool.execute(() -> {
                try {
                    Thread.sleep(finalI * 1000); //让任务执行慢点
                    System.out.println(Thread.currentThread().getName() + ":" + ai.incrementAndGet());
                    Thread.sleep(finalI * 2500); //让任务执行慢点
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(20000);
        executorPool.shutdown();
        Thread.sleep(10000);
        monitor.shutdown();


    }
}
