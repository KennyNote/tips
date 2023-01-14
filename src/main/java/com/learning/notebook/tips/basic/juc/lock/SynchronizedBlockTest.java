package com.learning.notebook.tips.basic.juc.lock;

/**
 * @author Kenny Liu
 * @version 2019-11-06
 **/
public class SynchronizedBlockTest {public static void main(String[] args) throws InterruptedException {
    // 其他线程执行对象中synchronized同步方法（上一节我们介绍过，需要回顾的可以看上一节的文章）和synchronized(this)代码块时呈现同步效果;
    // 如果两个线程使用了同一个“对象监视器”,运行结果同步，否则不同步.

    // synchronized关键字加到static方法和synchronized(class)代码块上都是是给Class类上锁，
    SynchronizedBlock_Service_Class service_class = new SynchronizedBlock_Service_Class();
    SynchronizedBlock_Object object_a = new SynchronizedBlock_Object();
    SynchronizedBlock_Object object_b = new SynchronizedBlock_Object();
    SynchronizedBlock_Thread a = new SynchronizedBlock_Thread(service_class, object_a);
    SynchronizedBlock_Thread b = new SynchronizedBlock_Thread(service_class, object_b);
    a.start();
    b.start();

    a.join();
    b.join();
    System.out.println("-------------------------------");

    // synchronized关键字加到非static方法上是给对象上锁。
    SynchronizedBlock_Service_Object service = new SynchronizedBlock_Service_Object();
    SynchronizedBlock_Object object = new SynchronizedBlock_Object();
    SynchronizedBlock_Thread c = new SynchronizedBlock_Thread(service, object);
    SynchronizedBlock_Thread d = new SynchronizedBlock_Thread(service, object);
    c.start();
    d.start();

}

}



class SynchronizedBlock_Object {
}

interface SynchronizedBlock_Service{
    void synchronized_method(SynchronizedBlock_Object object);
}

class SynchronizedBlock_Thread extends Thread {

    private SynchronizedBlock_Service service;
    private SynchronizedBlock_Object object;

    public SynchronizedBlock_Thread(SynchronizedBlock_Service service, SynchronizedBlock_Object object) {
        super();
        this.service = service;
        this.object = object;
    }

    @Override
    public void run() {
        super.run();
        service.synchronized_method(object);
    }
}

class SynchronizedBlock_Service_Class implements SynchronizedBlock_Service{

    @Override
    public void synchronized_method(SynchronizedBlock_Object object) {
        // synchronized关键字加到static方法和synchronized(class)代码块上都是是给Class类上锁，
        synchronized (SynchronizedBlock_Object.class) {
            try {
                System.out.println("synchronized_method getLock time="
                        + System.currentTimeMillis() + " run ThreadName="
                        + Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println("synchronized_method releaseLock time="
                        + System.currentTimeMillis() + " run ThreadName="
                        + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class SynchronizedBlock_Service_Object implements SynchronizedBlock_Service{

    @Override
    public void synchronized_method(SynchronizedBlock_Object object) {
        // synchronized关键字加到非static方法上是给对象上锁。
        synchronized (object) {
            try {
                System.out.println("synchronized_method getLock time="
                        + System.currentTimeMillis() + " run ThreadName="
                        + Thread.currentThread().getName());
                Thread.sleep(2000);
                System.out.println("synchronized_method releaseLock time="
                        + System.currentTimeMillis() + " run ThreadName="
                        + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}