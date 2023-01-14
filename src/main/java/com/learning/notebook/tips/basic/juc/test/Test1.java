package com.learning.notebook.tips.basic.juc.test;

public class Test1 {

    private volatile static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(() -> {
            System.out.println("线程A开始循环");
            while (!flag) {

            }
            System.out.println("线程A结束循环");
        });
        Thread threadB = new Thread(() -> {
            System.out.println("线程B正在执行");
            flag = true;
        });

        threadA.start();
        Thread.sleep(1000);
        threadB.start();
    }
}
