package com.learning.notebook.tips.basic.juc.util;

import java.util.concurrent.Phaser;

public class PhaserTest {

    public static void main(String[] args) {
        replaceCountDownLatch();
        //        replaceCyclicBarrier();
    }

    public static void replaceCountDownLatch() {

        /**
         * Phaser代替CountDownLatch： arrive、awaitAdvance
         */

        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (phaser) {
                    System.out.println(Thread.currentThread().getName() + " running");
                    phaser.arrive();
                }
            }, "thread" + i).start();
        }
        System.out.println(Thread.currentThread().getName() + " wait");
        // 等待此移相器的相位从给定的相位值前进，如果当前相位不等于给定的相位值或此移相器终止，则立即返回。
        phaser.awaitAdvance(phaser.getPhase());
        System.out.println(Thread.currentThread().getName() + " 线程执行完毕");
    }

    public static void replaceCyclicBarrier() {

        /**
         * 用phaser代替CyclicBarrier： arriveAndAwaitAdvance
         */

        Phaser phaser = new Phaser(5);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("=========" + Thread.currentThread().getName());
                // 到达这个移相器并等待其他人，都完成再进行下一步。
                phaser.arriveAndAwaitAdvance();
                System.out.println("*********" + Thread.currentThread().getName());
                // 到达这个移相器并等待其他人，都完成再进行下一步。
                phaser.arriveAndAwaitAdvance();
                System.out.println("#########" + Thread.currentThread().getName());
            }).start();
        }
    }
}
