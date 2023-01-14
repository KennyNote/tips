package com.learning.notebook.tips.basic.juc.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Kenny Liu
 * @version 2019-11-07
 **/
public class ReentrantReadWriteLockTest {
    /**
     * 默认情况下ReentranLock（排他锁）类使用的是非公平锁。且排他锁同一时刻只允许一个线程访问
     * @param args
     */
    public static void main(String[] args) {

        Service_ReentrantReadWriteLock service = new Service_ReentrantReadWriteLock();

        Thread_ReentrantReadWriteLock a1 = new Thread_ReentrantReadWriteLock(service);
        Thread_ReentrantReadWriteLock a2 = new Thread_ReentrantReadWriteLock(service);
        Thread_ReentrantReadWriteLock a3 = new Thread_ReentrantReadWriteLock(service);

        a1.start();
        a2.start();
        a3.start();

        System.out.println("------------------");

        try {
            Thread_ReentrantReadWriteLock_Read read1 = new Thread_ReentrantReadWriteLock_Read(service);
            Thread_ReentrantReadWriteLock_Read read2 = new Thread_ReentrantReadWriteLock_Read(service);
            read1.start();
            read2.start();
            // 线程进入读锁的前提条件：没有其他线程的写锁，共享锁。
            // result
            // 获得读锁Thread-3
            // 获得读锁Thread-4
            // 解开读锁Thread-3
            // 解开读锁Thread-4
            Thread.sleep(1000);

            Thread_ReentrantReadWriteLock_Write write1 = new Thread_ReentrantReadWriteLock_Write(service);
            Thread_ReentrantReadWriteLock_Write write2 = new Thread_ReentrantReadWriteLock_Write(service);
            write1.start();
            write2.start();
            // 线程进入写锁的前提条件：没有其他线程的读锁，没有其他线程的写锁，排他锁。
            // result
            // 获得写锁Thread-5
            // 解开写锁Thread-5
            // 获得写锁Thread-6
            // 解开写锁Thread-6
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Service_ReentrantReadWriteLock {

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void service() {
        lock.writeLock().lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
            }
        } finally {
            lock.writeLock().unlock();
        }

    }

    public void read() {
        try {
            try {
                lock.readLock().lock();
                System.out.println("获得读锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                Thread.sleep(10000);
            } finally {
                System.out.println("解开读锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                lock.readLock().unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            try {
                lock.writeLock().lock();
                System.out.println("获得写锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                Thread.sleep(10000);
            } finally {
                System.out.println("解开写锁" + Thread.currentThread().getName()
                        + " " + System.currentTimeMillis());
                lock.writeLock().unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Thread_ReentrantReadWriteLock extends Thread {

    private Service_ReentrantReadWriteLock service;

    public Thread_ReentrantReadWriteLock(Service_ReentrantReadWriteLock service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.service();
    }
}

class Thread_ReentrantReadWriteLock_Read extends Thread {

    private Service_ReentrantReadWriteLock service;

    public Thread_ReentrantReadWriteLock_Read(Service_ReentrantReadWriteLock service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.read();
    }
}

class Thread_ReentrantReadWriteLock_Write extends Thread {

    private Service_ReentrantReadWriteLock service;

    public Thread_ReentrantReadWriteLock_Write(Service_ReentrantReadWriteLock service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.write();
    }
}