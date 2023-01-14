package com.learning.notebook.tips.basic.juc;

/**
 * @author Kenny Liu
 * @version 2019-11-06
 **/
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        // join方法的作用，在当前线程中调用一个线程的 join() 方法，则当前线程转为阻塞状态，直到另一个线程结束，当前线程再由阻塞状态变为就绪状态，等待 cpu 的宠幸。
        // 很多情况下，主线程生成并启动了子线程，需要用到子线程返回的结果，也就是需要主线程需要在子线程结束后再结束，这时候就要用到 join() 方法。
        // Thread.sleep(2000)不会释放锁，threadTest.join(2000)会释放锁，
        System.out.println(Thread.currentThread().getName() + " start");
        ThreadJoinA t1 = new ThreadJoinA("A");
        ThreadJoinB t2 = new ThreadJoinB("B");
        ThreadJoinC t3 = new ThreadJoinC("C");

        System.out.println("Thread_Join_1 start");
        t1.start();
        System.out.println("Thread_Join_1 end");

        System.out.println("Thread_Join_2 start");
        t2.start();
        System.out.println("Thread_Join_2 end");

        t1.join();// 主线程在t1.join()方法处停止，并需要等待A线程执行完毕后，主线程才会执行t3.start()，但是并不影响B线程的执行。
        System.out.println("Thread_Join_1 join");

        System.out.println("Thread_Join_3 start");
        t3.start();
        System.out.println("Thread_Join_3 end");

        System.out.println(Thread.currentThread().getName() + " end");
    }

}

class ThreadJoinA extends Thread {

    private String name;

    public ThreadJoinA(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            try {
                System.out.println(name + "-" + i);
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        super.run();
    }

}

class ThreadJoinB extends Thread {

    private String name;

    public ThreadJoinB(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            try {
                System.out.println(name + "-" + i);
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        super.run();
    }

}

class ThreadJoinC extends Thread {

    private String name;

    public ThreadJoinC(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 20; i++) {
            try {
                System.out.println(name + "-" + i);
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        super.run();
    }

}