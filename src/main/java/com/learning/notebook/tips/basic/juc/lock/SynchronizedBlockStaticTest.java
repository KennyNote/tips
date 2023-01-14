package com.learning.notebook.tips.basic.juc.lock;

/**
 * @author Kenny Liu
 * @version 2019-11-06
 **/
public class SynchronizedBlockStaticTest {
    public static void main(String[] args) {
        SynchronizedBlock_Service_Static service_static = new SynchronizedBlock_Service_Static();
        SynchronizedBlock_Object_Static object_a = new SynchronizedBlock_Object_Static();
        SynchronizedBlock_Object_Static object_b = new SynchronizedBlock_Object_Static();
        SynchronizedBlock_Thread_Static a = new SynchronizedBlock_Thread_Static(service_static, object_a);
        SynchronizedBlock_Thread_Static b = new SynchronizedBlock_Thread_Static(service_static, object_b);
        a.start();
        b.start();
    }
}

class SynchronizedBlock_Object_Static {
}


class SynchronizedBlock_Thread_Static extends Thread {

    private SynchronizedBlock_Service_Static service;
    private SynchronizedBlock_Object_Static object;

    public SynchronizedBlock_Thread_Static(SynchronizedBlock_Service_Static service, SynchronizedBlock_Object_Static object) {
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

class SynchronizedBlock_Service_Static {

    public static synchronized void synchronized_method(SynchronizedBlock_Object_Static object) {
        // synchronized关键字加到static方法和synchronized(class)代码块上都是是给Class类上锁，
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