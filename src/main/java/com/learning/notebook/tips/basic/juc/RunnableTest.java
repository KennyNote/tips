package com.learning.notebook.tips.basic.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kenny Liu
 * @version 2019-11-07
 **/
public class RunnableTest {

    public static void main(String[] args) throws InterruptedException {
        int count = 30;
        ServiceRunnable runnableService = new ServiceRunnable(count);

        Runnable runnable_1 = () -> {
            while (runnableService.testService_RunnableWithSynchronizedMethod()){}
        };

        Runnable runnable_2 = () -> runnableService.testService_RunnableWithSynchronizedBlock();
        Runnable runnable_3 = () -> runnableService.testService_RunnableWithLock();


        Thread runnableThread_1 = new Thread(runnable_1);
        Thread runnableThread_2 = new Thread(runnable_1);
        Thread runnableThread_3 = new Thread(runnable_1);
        runnableThread_1.start();
        runnableThread_2.start();
        runnableThread_3.start();

        runnableThread_1.join();
        runnableThread_2.join();
        runnableThread_3.join();

        System.out.println("----------------");

        Thread runnableThread_4 = new Thread(runnable_2);
        Thread runnableThread_5 = new Thread(runnable_2);
        Thread runnableThread_6 = new Thread(runnable_2);
        runnableThread_4.start();
        runnableThread_5.start();
        runnableThread_6.start();

        runnableThread_4.join();
        runnableThread_5.join();
        runnableThread_6.join();

        System.out.println("----------------");

        Thread runnableThread_7 = new Thread(runnable_3);
        Thread runnableThread_8 = new Thread(runnable_3);
        Thread runnableThread_9 = new Thread(runnable_3);
        runnableThread_7.start();
        runnableThread_8.start();
        runnableThread_9.start();


    }


}

/**
 * synchronized关键字与wait()和notify/notifyAll()方法相结合可以实现等待/通知机制，
 * ReentrantLock类需要借助于Condition接口与newCondition() 方法
 */
class ServiceRunnable {

    private int balance;
    private final Lock lock;
    private final Condition condition;

    public ServiceRunnable(int count) {
        this.balance = count;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public synchronized boolean testService_RunnableWithSynchronizedMethod() {
        if (balance > 20) {
            try {
                balance = balance - 1;
                System.out.println(Thread.currentThread().getName() + ":" + balance);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        } else {
            return false;
        }
    }

    public void testService_RunnableWithSynchronizedBlock() {
        while (true) {
            synchronized (this) {
                notify();
                try {
                    if (balance > 10) {
                        balance = balance - 1;
                        System.out.println(Thread.currentThread().getName() + ":" + balance);
                        wait();
                        Thread.sleep(50);
                    } else {
                        break;
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void testService_RunnableWithLock() {
        while (true) {
            lock.lock();
            condition.signal();
            try {
                if (balance > 0) {
                    balance = balance - 1;
                    System.out.println(Thread.currentThread().getName() + ":" + balance);
                    condition.await();
                    Thread.sleep(50);
                } else {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}
