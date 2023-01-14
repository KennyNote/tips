package com.learning.notebook.tips.basic.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kenny Liu
 * @version 2019-11-07
 **/
public class CallableTest {

    public static void main(String[] args) throws InterruptedException {
        ServiceCallable callableService = new ServiceCallable(new ArrayList<Integer>());

        Callable callable1 = () -> {
            List<Integer> list;
            while ((list = callableService.testServiceCallableWithSynchronizedMethod()).size() < 10) {
            }
            return list;
        };

        Callable callable2 = () -> callableService.testServiceCallableWithSynchronizedBlock();

        Callable callable3 = () -> callableService.testServiceCallableWithLock();

        FutureTask<Integer> future1 = new FutureTask(callable1);
        FutureTask<Integer> future2 = new FutureTask(callable1);
        FutureTask<Integer> future3 = new FutureTask(callable1);

        Thread callableThread1 = new Thread(future1);
        Thread callableThread2 = new Thread(future2);
        Thread callableThread3 = new Thread(future3);
        callableThread1.start();
        callableThread2.start();
        callableThread3.start();

        try {
            System.out.println(future1.get());
            System.out.println(future2.get());
            System.out.println(future3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

/**
 * synchronized关键字与wait()和notify/notifyAll()方法相结合可以实现等待/通知机制，
 * ReentrantLock类需要借助于Condition接口与newCondition() 方法
 */
class ServiceCallable {

    private List<Integer> list;
    private Lock lock;
    private Condition condition;

    public ServiceCallable(List<Integer> list) {
        this.list = list;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public synchronized List<Integer> testServiceCallableWithSynchronizedMethod() {
        if (list.size() < 10) {
            try {
                list.add(list.size() + 1);
                System.out.println(Thread.currentThread().getName() + ":" + list.size());
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return list;
        } else {
            return list;
        }
    }

    public List<Integer> testServiceCallableWithSynchronizedBlock() {
        while (true) {
            synchronized (this) {
                notify();
                try {
                    if (list.size() < 20) {
                        list.add(list.size() + 1);
                        System.out.println(Thread.currentThread().getName() + ":" + list.size());
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
        return list;
    }

    public List<Integer> testServiceCallableWithLock() {
        while (true) {
            lock.lock();
            condition.signal();
            try {
                if (list.size() < 30) {
                    list.add(list.size() + 1);
                    System.out.println(Thread.currentThread().getName() + ":" + list.size());
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
        return list;
    }

}