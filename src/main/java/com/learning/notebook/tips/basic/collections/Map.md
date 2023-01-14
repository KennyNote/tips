# Map

## HashMap

### put方法

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
  	// 如果当前的table为null或者为空，则调用resize()方法进行初始化。
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
  	// 如果hash运算完的位置当前没有存在节点，直接插入。
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
  	// 如果当前位置存在节点
    else {
        Node<K,V> e; K k;
      	// 判断key本身是否相同，相同则直接返回。
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
      	// 判断前位置存在节点的结构是否是二叉树，二叉树的话则调用putTreeVal。
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
      	// 以上都不是，说明需要按照链表的情况处理了。
        else {
          	// 循环处理
            for (int binCount = 0; ; ++binCount) {
              	// 如果当前位置存在节点，且只有一个，把最新的节点进行尾插。
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                  	// 插入之后判断binCount > 树化的阈值了（TREEIFY_THRESHOLD == 8），binCount不包含头结点。
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
              	// 判断key本身是否相同，相同则直接返回。
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
      	// e!=null 说明存在旧值的key与要插入的key"相等"
        // 对于我们分析的put操作，下面这个 if 其实就是进行 ""，然后返回旧值
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
              	// 值覆盖
                e.value = value;
          	// HashMap中未实现，LinkedHashMap实现了。
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
  	// 如果 HashMap 由于新插入这个值导致 size 已经超过了阈值，需要进行扩容
    if (++size > threshold)
        resize();
  	// HashMap中未实现，LinkedHashMap实现了。
    afterNodeInsertion(evict);
    return null;
}
```

### resize方法

~~~java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
  	// 旧的table的容量
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    // 旧的table扩容阈值
  	int oldThr = threshold;
  	// 新的table的容量和扩容阈值
    int newCap, newThr = 0;
  	// 判断oldCap > 0，oldCap如果是正常的扩容是 > 0的，初始化时是oldCap == 0的。
    if (oldCap > 0) {
        // 判断oldCap > HashMap允许的最大容量。
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
          	// 直接返回oldTab不做处理
            return oldTab;
        }
       	// newCap = oldCap << 1，即两倍大小。并且若同时满足1.newCap < HashMap允许的最大容量，2.oldCap > 16，则newThr变成oldThr的两倍。
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
  	// 判断oldThr > 0。当第一次put节点，调用resize()初始化时，oldThr也是0。
    else if (oldThr > 0) 
        newCap = oldThr;
  	// 以上都不满足，说明table需要初始化，newCap = 16，newThr = 16 * 0.75 = 12
    else {               
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
  	// 对于newThr，此变量定义是0，后续的else if (oldThr > 0) 没有对他操作，所以当 oldCap == 0 && oldThr > 0会进入。但是为啥会出现这种？
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
  	// 给当前的map的threshold赋值最新的newThr
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
    Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
      	// 循环遍历旧的table
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
              	// 需要先把oldTab在j位置设置成null
                oldTab[j] = null;
              	// 只有一个节点，直接插入newTab
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
              	// 如果是红黑树，就split方法分配
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
              	// 链表的话，就是通过oldcap与hash做与运算，来判断扩容后要不要移动。
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                      	// 与运算结果 == 0，则原索引位上的链表中，重排后仍会处于原索引位的节点，但是这些节点之间不一定相邻。
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                       	// 与运算结果 != 0，则重排后将处于（原索引位+原hash table容量）的节点，这些节点之间也不一定相邻。
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    // 是null就代表原始链表的遍历结束，退出循环。
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
~~~

## ConcurrentHashMap

### put方法

~~~java
final V putVal(K key, V value, boolean onlyIfAbsent) {
    if (key == null || value == null) throw new NullPointerException();
  	// (h ^ (h >>> 16)) & HASH_BITS;
  	// h向右无符号移位16，则高16位都会为0
  	// 如果两个hashCode值的低16位相同，但是高位不同，经过异或操作，低16位会变得不一样了。
  	// 并且由于哈希表数组长度 n 会是偏小的数值，那么进行 (n - 1) & hash 运算时，一直使用的是hash较低位的值。那么即使hash值不同，但如果低位相当，也会发生碰撞。而进行 h ^ (h >>> 16) 加工后的hash值，让hashCode高位的值也参与了哈希运算，因此减少了碰撞的概率。
  	// HASH_BITS 这个常量的值为 0x7fffffff，转化为二进制为 0111 1111 1111 1111 1111 1111 1111 1111。这个操作后会把最高位转为 0，其实就是消除了符号位，得到的都是正数。这是因为负的 hashCode 在 ConcurrentHashMap 中有特殊的含义，因此我们需要得到一个正的 hashCode。
    int hash = spread(key.hashCode());
    int binCount = 0;
    for (Node<K,V>[] tab = table;;) {
        Node<K,V> f; int n, i, fh; K fk; V fv;
      	// 如果当前的table为null或者为空，则调用initTable()方法进行初始化。
        if (tab == null || (n = tab.length) == 0)
            tab = initTable();
      	// 如果hash运算完的位置当前没有存在节点，直接CAS插入。
        else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
                break;                   // no lock when adding to empty bin
        }
      	// 如果hash == -1，说明是 ForwardingNode 对象（这是一个占位符对象，保存了扩容后的容器）
        else if ((fh = f.hash) == MOVED)
          	// 帮助其扩容。以加快速度。
            tab = helpTransfer(tab, f);
        else if (onlyIfAbsent // check first node without acquiring lock
                 && fh == hash
                 && ((fk = f.key) == key || (fk != null && key.equals(fk)))
                 && (fv = f.val) != null)
            return fv;
        else {
            V oldVal = null;
          	// 同步 f 节点，防止增加链表的时候导致链表成环。
            synchronized (f) {
              	// else if ((f = tabAt(tab, i = (n - 1) & hash)) == null)的操作时不是线程安全的，有可能i位置不是f，故在同步后，重新判断。
                if (tabAt(tab, i) == f) {
                  	// 如果f节点的hash值fh >= 0
                    if (fh >= 0) {
                        // 使用binCount记录链表长度。
                        binCount = 1;
                      	// 死循环，直到将值添加到链表尾部，并计算链表的长度。
                        for (Node<K,V> e = f;; ++binCount) {
                            K ek;
                            if (e.hash == hash &&
                                ((ek = e.key) == key ||
                                 (ek != null && key.equals(ek)))) {
                                oldVal = e.val;
                                if (!onlyIfAbsent)
                                    e.val = value;
                                break;
                            }
                            Node<K,V> pred = e;
                            if ((e = e.next) == null) {
                                pred.next = new Node<K,V>(hash, key, value);
                                break;
                            }
                        }
                    }
                  	// 如果f节点的hash < 0，并且f是树类型。
                    else if (f instanceof TreeBin) {
                        Node<K,V> p;
                        binCount = 2;
                      	// 调用f节点的putTreeVal方法
                        if ((p = ((TreeBin<K,V>)f).putTreeVal(hash, key,
                                                       value)) != null) {
                            oldVal = p.val;
                            if (!onlyIfAbsent)
                                p.val = value;
                        }
                    }
                    else if (f instanceof ReservationNode)
                        throw new IllegalStateException("Recursive update");
                }
            }
          	// 链表长度大于等于8时，将该节点改成红黑树树。
            if (binCount != 0) {
                if (binCount >= TREEIFY_THRESHOLD)
                    treeifyBin(tab, i);
                if (oldVal != null)
                    return oldVal;
                break;
            }
        }
    }
  	// 判断是否需要扩容
    addCount(1L, binCount);
    return null;
}
~~~

### initTable方法
~~~java
/**
  * 该方法为了在并发环境下的安全，加入了一个 sizeCtl 变量来进行判断，只有当一个线程通过CAS修改该变量成功后（默认为0，改成 -1），该线程才能初始化数组。
  */
private final Node<K,V>[] initTable() {
    Node<K,V>[] tab; int sc;
    while ((tab = table) == null || tab.length == 0) {
      	// sc < 0 说明被其他线程改了
        if ((sc = sizeCtl) < 0)
          	// 放弃CPU资源，进行while自旋等待。
            Thread.yield(); // lost initialization race; just spin
        else if (U.compareAndSetInt(this, SIZECTL, sc, -1)) {
            try {
                if ((tab = table) == null || tab.length == 0) {
                  	// sc 在初始化的时候用户可能会自定义，如果没有自定义，则是默认的
                    int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
                    @SuppressWarnings("unchecked")
                    Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n];
                    table = tab = nt;
                  	// sc 计算后作为扩容的阀值，sc默认会赋值16 - 16 >>> 2 = 12
                    sc = n - (n >>> 2);
                }
            } finally {
              	// 最终sc赋值给sizeCtl
                sizeCtl = sc;
            }
            break;
        }
    }
    return tab;
}
~~~

### addCount方法
~~~java
/**
  * 从 putVal 传入的参数是(1， binCount)，binCount 默认是0，只有 hash 冲突了才会大于 1。且他的大小是链表的长度（如果不是红黑数结构的话）。
  * x 参数表示的此次需要对表中元素的个数加几。check 参数表示是否需要进行扩容检查，大于等于0 需要进行检查。
  * putVal 方法的 binCount 参数最小也是 0 ，因此，每次添加元素都会进行检查。（除非是覆盖操作）
  * ⭐️sizeCtl 是关键，该变量高 16 位保存 length 生成的标识符，低 16 位保存并发扩容的线程数，通过这连个数字，可以判断出，是否结束扩容了。
  */
private final void addCount(long x, int check) {
    CounterCell[] cs; long b, s;
  	// 此if主要是查看是否有并发问题
  	// 1.cs != null => 之前有过并发导致CAS尝试修改baseCount变量失败，导致使用了counterCells，一旦用过counterCells则后续都要用它来计数，并发问题则看下个if的具体情况了。
  	// 2.cs == null，CAS尝试修改baseCount变量，若失败说明存在并发问题，转入多线程处理方式；若成功说明不存在存在并发问题，不进入if。
    if ((cs = counterCells) != null ||
        !U.compareAndSetLong(this, BASECOUNT, b = baseCount, s = b + x)) {
        CounterCell c; long v; int m;
        boolean uncontended = true;
      	// 1.cs == null，也说明CAS尝试修改baseCount变量一定是失败了，存在并发问题，会触发fullAddCount 方法重新死循环插入。
        // 2.或者cs.length-1 == 0（前提cs != null），？
        // 3.或者cs[随机位置 & 数组长度] == null，？
        // 4.或者CAS尝试对CELLVALUE进行赋值为 v + x，若失败说明存在并发问题，会触发fullAddCount 方法重新死循环插入。
        if (cs == null || (m = cs.length - 1) < 0 ||
            (c = cs[ThreadLocalRandom.getProbe() & m]) == null ||
            !(uncontended =
              U.compareAndSetLong(c, CELLVALUE, v = c.value, v + x))) {
            fullAddCount(x, uncontended);
            return;
        }
        // 走到这个说明没有执行fullAddCount，说明没有冲突问题和并发问题（1.cs == null情况下，CAS尝试修改baseCount变量成功，cs != null，CAS尝试对CELLVALUE进行赋值为 v + x成功）。
      	// 检查 check 变量，如果该变量小于等于 1，直接结束。否则，计算一下 count 变量。
      	if (check <= 1)
            return;
        s = sumCount();
    }
  	// 如果需要检查,检查是否需要扩容，在 putVal 方法调用时，默认就是要检查的。
    if (check >= 0) {
        Node<K,V>[] tab, nt; int n, sc;
      	// 1.如果map.size()，即s > sizeCtl（达到扩容阈值需要扩容）
      	// 2.table != null
        // 3.table 的长度小于 1 << 30。（可以扩容）
        while (s >= (long)(sc = sizeCtl) && (tab = table) != null &&
               (n = tab.length) < MAXIMUM_CAPACITY) {
          	// 根据resizeStamp得到一个标识，并且左移位16位（RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS，RESIZE_STAMP_BITS = 16）
          	// resizeStamp 高16位代表扩容的标记、低16位代表并行扩容的线程数
            int rs = resizeStamp(n) << RESIZE_STAMP_SHIFT;
          	// 如果阈值小于0，说明正在扩容。
            if (sc < 0) {
              	// sc == rs + MAX_RESIZERS，即帮助线程已到达最多线程数（MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1 = 1 << 16 - 1 = 65535）
              	// sc == rs + 1，即说明扩容结束了，不再有线程进行扩容（扩容时默认第一个线程设置在transfer之前调用了U.compareAndSetInt(this, SIZECTL, sc, rs + 2)，使sc == rs + 2，当第一个线程结束扩容了，就会将sc减1。这个时候，sc == rs + 1）
              	// nextable == null 或 transferIndex <= 0，说明已结束，直接break。
                if (sc == rs + MAX_RESIZERS || sc == rs + 1 ||
                    (nt = nextTable) == null || transferIndex <= 0)
                    break;
              	// 如果以上都不是, 将 sizeCtl + 1, （表示增加了一个线程帮助其扩容）
                if (U.compareAndSetInt(this, SIZECTL, sc, sc + 1))
                    transfer(tab, nt);
            }
          	// 如果 sc > 0，说明是扩容刚开始，设置sc = rs + 2，因为 sc < 0 时，表示有多少条线程在进行转移是：sc - 1
            else if (U.compareAndSetInt(this, SIZECTL, sc, rs + 2))
                transfer(tab, null);
            s = sumCount();
        }
    }
}
~~~

### resizeStamp方法

~~~java
/**
  * Returns the stamp bits for resizing a table of size n.
  * Must be negative when shifted left by RESIZE_STAMP_SHIFT.
  */
static final int resizeStamp(int n) {
  	// 返回值rs定为一个负数。
  	// Integer.numberOfLeadingZeros()方法返回指定int值的二进制补码二进制表示形式中最高位（“最左”）一位之前的零位数目。
  	// 1 << 15 为1000 0000 0000 0000。
  	// 两者或以后，那么第15位肯定为1。后续resizeStamp(n) << RESIZE_STAMP_SHIFT，即再左移16位(Int4字节32位)，第一位为1则是负数。由此，我们会得到一个负数。
  	// resizeStamp可以保证每次扩容都生成唯一的生成戳，每次新的扩容，都有一个不同的n，这个生成戳就是根据n来计算出来的一个数字，n不同，这个数字也不同
  	return Integer.numberOfLeadingZeros(n) | (1 << (RESIZE_STAMP_BITS - 1));
}
~~~

### helpTransfer方法

~~~java
/**
  * ⭐️sizeCtl 是关键，该变量高 16 位保存 length 生成的标识符，低 16 位保存并发扩容的线程数，通过这连个数字，可以判断出，是否结束扩容了。
  */
final Node<K,V>[] helpTransfer(Node<K,V>[] tab, Node<K,V> f) {
    Node<K,V>[] nextTab; int sc;
  	// 如果当前的table不为null，并且table中的f节点为ForwardingNode，以及f的nextTable（新 table）不是空，则帮助扩容。
    if (tab != null && (f instanceof ForwardingNode) &&
        (nextTab = ((ForwardingNode<K,V>)f).nextTable) != null) {
      	// 针对rs的操作在addCount也有
      	// 根据resizeStamp得到一个标识，并且左移位16位（RESIZE_STAMP_SHIFT = 32 - RESIZE_STAMP_BITS，RESIZE_STAMP_BITS = 16）
      	// resizeStamp 高16位代表扩容的标记、低16位代表并行扩容的线程数
        int rs = resizeStamp(tab.length) << RESIZE_STAMP_SHIFT;
      	// 如果 nextTab 没有被并发修改，且tab也没有被并发修改，且sizeCtl < 0 （说明还在扩容）
        while (nextTab == nextTable && table == tab &&
               (sc = sizeCtl) < 0) {
            // sc == rs + MAX_RESIZERS，即帮助线程已到达最多线程数（MAX_RESIZERS = (1 << (32 - RESIZE_STAMP_BITS)) - 1 = 1 << 16 - 1 = 65535）
           	// sc == rs + 1，即说明扩容结束了，不再有线程进行扩容（扩容时默认第一个线程设置在transfer之前调用了U.compareAndSetInt(this, SIZECTL, sc, rs + 2)，使sc == rs + 2，当第一个线程结束扩容了，就会将sc减1。这个时候，sc == rs + 1）
          	// transferIndex <= 0，说明已结束，直接break。
            if (sc == rs + MAX_RESIZERS || sc == rs + 1 ||
                transferIndex <= 0)
                break;
          	// 如果以上都不是, 将 sizeCtl + 1, （表示增加了一个线程帮助其扩容）
            if (U.compareAndSetInt(this, SIZECTL, sc, sc + 1)) {
                transfer(tab, nextTab);
                break;
            }
        }
        return nextTab;
    }
    return table;
}
~~~

### transfer方法
~~~java
private final void transfer(Node<K,V>[] tab, Node<K,V>[] nextTab) {
    int n = tab.length, stride;
  	// 将 length / 8 然后除以 CPU核心数。如果得到的结果小于 16，那么就使用 16。
  	// 这里的目的是让每个 CPU 处理的桶一样多，避免出现转移任务不均匀的现象，如果桶较少的话，默认一个 CPU（一个线程）处理 16 个桶
    if ((stride = (NCPU > 1) ? (n >>> 3) / NCPU : n) < MIN_TRANSFER_STRIDE)
        stride = MIN_TRANSFER_STRIDE; // subdivide range
  	// 新的 table 尚未初始化
    if (nextTab == null) {            // initiating
        try {
            @SuppressWarnings("unchecked")
          	// 扩容  2 倍
            Node<K,V>[] nt = (Node<K,V>[])new Node<?,?>[n << 1];
          	// 更新
            nextTab = nt;
        } catch (Throwable ex) {      // try to cope with OOME
          	// 扩容失败， sizeCtl 使用 int 最大值。
            sizeCtl = Integer.MAX_VALUE;
            return;
        }
      	// 更新成员变量
        nextTable = nextTab;
      	// 更新转移下标，就是旧tab的length
        transferIndex = n;
    }
  	// 新tab 的 length
    int nextn = nextTab.length;
  	// 创建一个 fwd 节点，用于占位。当别的线程发现这个槽位中是 fwd 类型的节点，则跳过这个节点。
    ForwardingNode<K,V> fwd = new ForwardingNode<K,V>(nextTab);
  	// 首次推进为 true，如果等于 true，说明需要再次推进一个下标（i--），反之，如果是 false，那么就不能推进下标，需要将当前的下标处理完毕才能继续推进。
    boolean advance = true;
  	// 完成状态，如果是 true，就结束此方法。
    boolean finishing = false; // to ensure sweep before committing nextTab
  	// 死循环,i 表示下标，bound 表示当前线程可以处理的当前桶区间最小下标
    for (int i = 0, bound = 0;;) {
        Node<K,V> f; int fh;
        while (advance) {
            int nextIndex, nextBound;
            if (--i >= bound || finishing)
                advance = false;
            else if ((nextIndex = transferIndex) <= 0) {
                i = -1;
                advance = false;
            }
            else if (U.compareAndSetInt
                     (this, TRANSFERINDEX, nextIndex,
                      nextBound = (nextIndex > stride ?
                                   nextIndex - stride : 0))) {
                bound = nextBound;
                i = nextIndex - 1;
                advance = false;
            }
        }
        if (i < 0 || i >= n || i + n >= nextn) {
            int sc;
            if (finishing) {
                nextTable = null;
                table = nextTab;
                sizeCtl = (n << 1) - (n >>> 1);
                return;
            }
            if (U.compareAndSetInt(this, SIZECTL, sc = sizeCtl, sc - 1)) {
                if ((sc - 2) != resizeStamp(n) << RESIZE_STAMP_SHIFT)
                    return;
                finishing = advance = true;
                i = n; // recheck before commit
            }
        }
        else if ((f = tabAt(tab, i)) == null)
            advance = casTabAt(tab, i, null, fwd);
        else if ((fh = f.hash) == MOVED)
            advance = true; // already processed
        else {
            synchronized (f) {
                if (tabAt(tab, i) == f) {
                    Node<K,V> ln, hn;
                    if (fh >= 0) {
                        int runBit = fh & n;
                        Node<K,V> lastRun = f;
                        for (Node<K,V> p = f.next; p != null; p = p.next) {
                            int b = p.hash & n;
                            if (b != runBit) {
                                runBit = b;
                                lastRun = p;
                            }
                        }
                        if (runBit == 0) {
                            ln = lastRun;
                            hn = null;
                        }
                        else {
                            hn = lastRun;
                            ln = null;
                        }
                        for (Node<K,V> p = f; p != lastRun; p = p.next) {
                            int ph = p.hash; K pk = p.key; V pv = p.val;
                            if ((ph & n) == 0)
                                ln = new Node<K,V>(ph, pk, pv, ln);
                            else
                                hn = new Node<K,V>(ph, pk, pv, hn);
                        }
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                    else if (f instanceof TreeBin) {
                        TreeBin<K,V> t = (TreeBin<K,V>)f;
                        TreeNode<K,V> lo = null, loTail = null;
                        TreeNode<K,V> hi = null, hiTail = null;
                        int lc = 0, hc = 0;
                        for (Node<K,V> e = t.first; e != null; e = e.next) {
                            int h = e.hash;
                            TreeNode<K,V> p = new TreeNode<K,V>
                                (h, e.key, e.val, null, null);
                            if ((h & n) == 0) {
                                if ((p.prev = loTail) == null)
                                    lo = p;
                                else
                                    loTail.next = p;
                                loTail = p;
                                ++lc;
                            }
                            else {
                                if ((p.prev = hiTail) == null)
                                    hi = p;
                                else
                                    hiTail.next = p;
                                hiTail = p;
                                ++hc;
                            }
                        }
                        ln = (lc <= UNTREEIFY_THRESHOLD) ? untreeify(lo) :
                            (hc != 0) ? new TreeBin<K,V>(lo) : t;
                        hn = (hc <= UNTREEIFY_THRESHOLD) ? untreeify(hi) :
                            (lc != 0) ? new TreeBin<K,V>(hi) : t;
                        setTabAt(nextTab, i, ln);
                        setTabAt(nextTab, i + n, hn);
                        setTabAt(tab, i, fwd);
                        advance = true;
                    }
                    else if (f instanceof ReservationNode)
                        throw new IllegalStateException("Recursive update");
                }
            }
        }
    }
}
~~~