package com.learning.notebook.tips.basic.juc.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {

    /**
     * synchronized 是通过 Object 的 wait / notify 实现等待-唤醒，每次唤醒的是一个随机等待的线程。
     * ReentrantLock 搭配 Condition 类实现等待-唤醒，可以更精确控制唤醒某个指定的线程。
     */

    private static volatile String nextPrintIs = "A";
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition conditionA = lock.newCondition();
    private static final Condition conditionB = lock.newCondition();
    private static final Condition conditionC = lock.newCondition();

    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("A get lock");
                while (!nextPrintIs.equals("A")) {
                    conditionA.await();
                }
                for (int i = 0; i < 3; i++) {
                    System.out.println("A - " + (i + 1));
                }
                nextPrintIs = "B";
                //通知conditionB实例的线程运行
                conditionB.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("B get lock");
                while (!nextPrintIs.equals("B")) {
                    conditionB.await();
                }
                for (int i = 0; i < 3; i++) {
                    System.out.println("B - " + (i + 1));
                }
                nextPrintIs = "C";
                //通知conditionC实例的线程运行
                conditionC.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("C get lock");
                while (!nextPrintIs.equals("C")) {
                    conditionC.await();
                }
                for (int i = 0; i < 3; i++) {
                    System.out.println("C - " + (i + 1));
                }
                nextPrintIs = "A";
                //通知conditionA实例的线程运行
                conditionA.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
        // get lock 输出谁都可能第一，但是后续输出顺序一定是A => B => C
        //        A get lock
        //        A - 1
        //        A - 2
        //        A - 3
        //        C get lock
        //        B get lock
        //        B - 1
        //        B - 2
        //        B - 3
        //        C - 1
        //        C - 2
        //        C - 3
    }
}
