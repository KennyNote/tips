package com.learning.notebook.tips.basic.juc.threadpool;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadMonitor extends Thread{

    private ThreadPoolExecutor executor;

    private int seconds;

    private boolean run = true;

    public ThreadMonitor(ThreadPoolExecutor executor, int delay) {
        this.executor = executor;
        this.seconds = delay;
    }

    @Override
    public void run() {
        while (run) {
            String str = String.format("[线程监视器] [线程池中的线程数：%d/核心线程数：%d] 队列中的任务数：%d, 执行任务中的线程数: %d, 已完成任务的进程数: %d, 任务的数量: %d, isShutdown: %s, isTerminated: %s",
                this.executor.getPoolSize(),
                this.executor.getCorePoolSize(),
                this.executor.getQueue().size(),
                this.executor.getActiveCount(),
                this.executor.getCompletedTaskCount(),
                this.executor.getTaskCount(),
                this.executor.isShutdown(),
                this.executor.isTerminated());
            System.out.println(str);
            try {
                Thread.sleep(seconds * 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void shutdown() {
        this.run = false;
    }
}
