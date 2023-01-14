package com.learning.notebook.tips.basic.juc.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kenny Liu
 * @version 2019-11-07
 **/
public class FairLockTest {

    public static void main(String[] args) {
        // true为公平锁，false为非公平锁
        final FairService service = new FairService(true);

        // 创建十个线程
        Thread[] threadArray = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(() -> {
                System.out.println("★线程" + Thread.currentThread().getName() + "运行了");
                service.service();
            });
        }

        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }

    }
}

class FairService {

    private final ReentrantLock lock;

    public FairService(boolean isFair) {
        super();
        lock = new ReentrantLock(isFair);
    }

    public void service() {
        lock.lock();
        try {
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "获得锁定");
        } finally {
            lock.unlock();
        }
    }
}