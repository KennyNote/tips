package com.learning.notebook.tips.basic.juc.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    /**
     * 循环屏障，它可以协同多个线程，让多个线程在这个屏障前等到，直到所有线程都到达了这个屏障时，再一起执行后面的操作。
     * 假如每个线程各有一个await，任何一个线程运行到await方法时就阻塞，直到第parties个参与线程调用了await方法才返回。
     */
    private static final CyclicBarrier barrier1 = new CyclicBarrier(2);
    private static final CyclicBarrier barrier2 = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            try {
                System.out.println("产品经理规划新需求");
                //放开栅栏1
                barrier1.await(); // 调用await方法的线程告诉CyclicBarrier自己已经到达同步点，然后当前线程被阻塞。直到parties个参与线程调用了await方法，
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                //放开栅栏1
                barrier1.await();
                System.out.println("开发人员开发新需求功能");
                //放开栅栏2
                barrier2.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        Thread thread3 = new Thread(() -> {
            try {
                //放开栅栏2
                barrier2.await();
                System.out.println("测试人员测试新功能");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        });

        System.out.println("早上：");
        System.out.println("测试人员来上班了...");
        thread3.start();
        System.out.println("产品经理来上班了...");
        thread1.start();
        System.out.println("开发人员来上班了...");
        thread2.start();
    }
}
