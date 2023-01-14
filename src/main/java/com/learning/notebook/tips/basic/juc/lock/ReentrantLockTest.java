package com.learning.notebook.tips.basic.juc.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kenny Liu
 * @version 2019-11-06
 **/
public class ReentrantLockTest {
    //“可重入锁”概念是：自己可以再次获取自己的内部锁，代码实现上就是一个被synchronized标识方法的内部再去调用另一个被synchronized标识的方法。
    // 一个线程获得了某个对象的锁，此时这个对象锁还没有释放，当其再次想要获取这个对象的锁的时候，该线程还是可以继续获取的，
    // 如果不可锁重入的话，就会造成死锁。
    // 可重入锁也支持在父子类继承的环境中。
    // **重点**：如果父类有一个带synchronized关键字的方法，子类继承并重写了这个方法，但由于同步不能继承，所以还是需要在子类方法中添加synchronized关键字。

    public static void main(String[] args) {
        ReentrantServiceBySynchronized reentrantServiceBySynchronized= new ReentrantServiceBySynchronized();
        ReentrantThreadBySynchronized t1 = new ReentrantThreadBySynchronized(reentrantServiceBySynchronized);
        ReentrantThreadBySynchronized t2 = new ReentrantThreadBySynchronized(reentrantServiceBySynchronized);
        ReentrantThreadBySynchronized t3 = new ReentrantThreadBySynchronized(reentrantServiceBySynchronized);
        t1.start();
        t2.start();
        t3.start();

        System.out.println("----------------------");

        ReentrantServiceByLock reentrantServiceByLock = new ReentrantServiceByLock();
        ReentrantThreadByLock t4 = new ReentrantThreadByLock(reentrantServiceByLock);
        ReentrantThreadByLock t5 = new ReentrantThreadByLock(reentrantServiceByLock);
        ReentrantThreadByLock t6 = new ReentrantThreadByLock(reentrantServiceByLock);
        t4.start();
        t5.start();
        t5.interrupt();
        t6.start();
    }

}

/**
 * with_synchronized
 */
class ReentrantServiceBySynchronized {

    public synchronized void service1() {
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "service1");
        service2();
    }

    public synchronized void service2() {
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "service2");
        service3();
    }

    public synchronized void service3() {
        System.out.println("ThreadName=" + Thread.currentThread().getName() + "service3");
    }
}

class ReentrantThreadBySynchronized extends Thread {

    private ReentrantServiceBySynchronized service;

    public ReentrantThreadBySynchronized(ReentrantServiceBySynchronized service) {
        super();
        this.service = service;
    }
    @Override
    public void run() {
        service.service1();
    }

}

/**
 * with_lock
 */
class ReentrantServiceByLock {

    private final Lock lock = new ReentrantLock();

    public void service() {
        lock.lock();
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }

        // 试图获取锁，但是失败就去立即进行其他事情
        if (lock.tryLock()){
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
                    Thread.sleep(2000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }else {
            System.out.println("ThreadName=" + Thread.currentThread().getName() + "CAN NOT get the lock");
        }

        // 试图获取锁，但是等待超时就去进行其他事情，避免死锁。
        try {
            if (lock.tryLock(20, TimeUnit.SECONDS)) {
                try {
                    for (int i = 0; i < 5; i++) {
                        System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
                        Thread.sleep(2000);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                System.out.println("ThreadName=" + Thread.currentThread().getName() + "CAN NOT get the lock");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        // 如果等待过程中如果有中断事件，那么会停止等待，立即返回.
        try {
            lock.lockInterruptibly();
            try {
                for (int i = 0; i < 5; i++) {
                    System.out.println("ThreadName=" + Thread.currentThread().getName() + (" " + (i + 1)));
                    Thread.sleep(2000);
                }
            } finally {
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

}

class ReentrantThreadByLock extends Thread {

    private final ReentrantServiceByLock service;

    public ReentrantThreadByLock(ReentrantServiceByLock service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.service();
    }
}