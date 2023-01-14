package com.learning.notebook.tips.basic.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        /**
         * 默认情况下CompletableFuture会使用公共的ForkJoinPool线程池，这个线程池默认创建的线程数是 CPU 的核数（也可以通过 JVM option:-Djava.util.concurrent.ForkJoinPool.common.parallelism 来设置ForkJoinPool线程池的线程数）。
         * 如果所有CompletableFuture共享一个线程池，那么一旦有任务执行一些很慢的 I/O 操作，就会导致线程池中所有线程都阻塞在 I/O 操作上，从而造成线程饥饿，进而影响整个系统的性能。所以，强烈建议你要根据不同的业务类型创建不同的线程池，以避免互相干扰
         */
        CompletableFuture.runAsync(() -> System.out.println("123"));

        System.out.println(CompletableFuture.supplyAsync(() -> "456").get());

        System.out.println("end");
    }

}
