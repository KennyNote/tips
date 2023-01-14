package com.learning.notebook.tips.basic.juc.util;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    /**
     * Exchanger用于进行两个线程之间的数据交换。它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。这两个线程通过exchange()方法交换数据，当一个线程先执行exchange()方法后，它会一直等待第二个线程也执行exchange()方法，当这两个线程到达同步点时，这两个线程就可以交换数据了。
     *
     * 线程A通过SynchronousQueue将数据a交给线程B；线程A通过Exchanger和线程B交换数据，线程A把数据a交给线程B，同时线程B把数据b交给线程A。可见，SynchronousQueue是交给一个数据，Exchanger是交换两个数据。
     */
    private static final Exchanger<String> exchanger = new Exchanger<>();

    public static void main(String[] args) throws InterruptedException {
        //        // 模拟阻塞线程.
        //        new Thread(() -> {
        //            try {
        //                String wares = "红烧肉";
        //                System.out.println(Thread.currentThread().getName() + "商品方正在等待金钱方，使用货物兑换为金钱。");
        //                Thread.sleep(2000);
        //                String money = exchanger.exchange(wares, 1000, TimeUnit.MILLISECONDS);
        //                System.out.println(Thread.currentThread().getName() + "商品方使用商品兑换了" + money);
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //                Thread.currentThread().interrupt();
        //            }
        //        }).start();
        //        // 模拟阻塞线程.
        //        new Thread(() -> {
        //            try {
        //                String money = "人民币";
        //                System.out.println(Thread.currentThread().getName() + "金钱方正在等待商品方，使用金钱购买食物。");
        //                Thread.sleep(4000);
        //                String wares = exchanger.exchange(money, 1000, TimeUnit.MILLISECONDS);
        //                System.out.println(Thread.currentThread().getName() + "金钱方使用金钱购买了" + wares);
        //            } catch (Exception ex) {
        //                ex.printStackTrace();
        //                Thread.currentThread().interrupt();
        //            }
        //        }).start();

        Exchanger<Integer> exchanger = new Exchanger<Integer>();
        new Producer("", exchanger).start();
        new Consumer("", exchanger).start();
        TimeUnit.SECONDS.sleep(7);
        System.exit(-1);
    }

    static class Producer extends Thread {

        private Exchanger<Integer> exchanger;
        private static int data = 0;

        Producer(String name, Exchanger<Integer> exchanger) {
            super("Producer-" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            for (int i = 1; i < 5; i++) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = i;
                    System.out.println(getName() + " 交换前:" + data);
                    data = exchanger.exchange(data);
                    System.out.println(getName() + " 交换后:" + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Consumer extends Thread {

        private Exchanger<Integer> exchanger;
        private static int data = 0;

        Consumer(String name, Exchanger<Integer> exchanger) {
            super("Consumer-" + name);
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            while (true) {
                data = 0;
                System.out.println(getName() + " 交换前:" + data);
                try {
                    TimeUnit.SECONDS.sleep(1);
                    data = exchanger.exchange(data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + " 交换后:" + data);
            }
        }
    }
}
