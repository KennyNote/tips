# ConcurrentHashMap源码学习（基于JDK17）

## 属性
~~~java
    /**
 *  AtomicLong通过循环CAS实现原子操作,缺点是当高并发下竞争比较激烈的时候,会出现大量的CAS失败,导致循环CAS次数大大增加，这种自旋是要消耗时间cpu时间片的，会占用大量cpu的时间，降低效率。那这个问题如何解决呢？
 *  JUC给我们提供了一个类，LongAdder, 它的作用和AtomicLong是一样的，都是一个实现了原子操作的累加器，
 *  LongAdder通过维护一个基准值base和 Cell 数组，多线程的时候多个线程去竞争Cell数组的不同的元素，进行cas累加操作，并且每个线程竞争的Cell的下标不是固定的，
 *  如果CAS失败，会重新获取新的下标去更新，从而极大地减少了CAS失败的概率，最后在将base 和 Cell数组的元素累加，获取到我们需要的结果。
 */

/**
 * // table数组
 * transient volatile Node<K,V>[] table;
 *
 * // 扩容过程中的新table, 扩容结束后会将nextTable设置为null
 * private transient volatile Node<K,V>[] nextTable;
 *
 * // LongAdder中的baseCount, 未发生竞争时 或者 当前LongAdder处于加锁状态时, 增量累积到baseCount中。
 * private transient volatile long baseCount;
 *
 * // sizeCtl < 0
 * // 1. -1表示当前table正在初始化(有线程正在创建table数组), 当前线程需要自旋等待
 * // 2. 表示当前table数组正在进行扩容, 高16位表示扩容的标识戳, 低16位表示: (1 + nThread) 当前参与并发扩容的线程数量(这个状态最重要)
 * // sizeCtl = 0
 * // 表示创建table数组时, 使用DEFAULT_CAPACITY为大小
 * // sizeCtl > 0
 * // 1. 如果table为初始化, 表示初始化大小
 * // 2. 如果table已经初始化, 表示下次扩容时的阈值(n * 0.75)
 * private transient volatile int sizeCtl;
 *
 * // 扩容过程中, 记录当前进度, 所有线程都需要从transferIndex中分配区间任务, 去执行自己相应的区间
 * private transient volatile int transferIndex;
 *
 * // LongAdder中的cellBusy
 * // 0 表示当前LongAdder对象无锁状态
 * // 1 表示当前LongAdder对象加锁状态, 只有一个对象能持有加锁状态
 * private transient volatile int cellsBusy;
 */

~~~