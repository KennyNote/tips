一、前言
Redis应该是大家都很熟透的一个内存数据库了，但每一个技术在使用时都可能存在一些些坑，打我们个措手不及，此次的分享就围绕Redis，来讲一下我对于Redis的学习、实践以及踩坑的心路历程。
二、Redis的回顾
对于Redis的使用来说，常见的几个场景如下。
- 计数器
- 缓存
- 消息队列(发布/订阅功能)
- 分布式锁实现
  Redis相比其他缓存，有一个非常大的优势，就是支持多种数据类型。https://redis.io/docs/data-types/，个人认为https://redis.io/官网应该说是最好的redis学习网站了。
  三、Redis的常见注意事项
    - 避免存储BigKey
        - 例如，String、Set 在存储 int 数据时，会采用整数编码存储。Hash、ZSet 在元素数量比较少时（可配置），会采用压缩列表（ziplist）存储，在存储比较多的数据时，才会转换为哈希表和跳表。
    - 注意key的长度
        - 在开发业务时，你需要提前预估整个 Redis 中写入 key 的数量，如果 key 数量达到了百万级别，那么，过长的 key 名也会占用过多的内存空间。
    - Del时注意key的结构，非String的时间复杂度是O(n)，关注n的大小，没lazy-free时n很大可能会阻塞；String开了lazy-free也是主线程，所以不要bigkey。对象释放的场景主要有del命令，过期键删除，内存淘汰等三种。对象释放是否采用懒释放取决于相关配置：
        - dbDelete：server.lazyfree_lazy_server_del
        - expire：server.lazyfree_lazy_expire （源码见expire.c）
        - freeMemoryIfNeeded：lazyfree_lazy_eviction（源码见evict.c）
    - 使用O(n)命令时，关注n的大小，n很大可能会阻塞。O(1) setbit也要关注偏移量。
    - 不使用复杂度过高命令。
        - 除了操作 bigkey 会导致后面请求发生排队之外，在执行复杂度过高的命令时，也会发生这种情况。因为执行复杂度过高的命令，会消耗更多的 CPU 资源，主线程中的其它请求只能等待，这时也会发生排队延迟。例如：SORT、SINTER、SINTERSTORE、ZUNIONSTORE、ZINTERSTORE
    - 批量命令代替循环单个命令。
        - 减少客户端、服务端的来回网络 IO 次数。
    - 设置 maxmemory
        - 虽然你的 Redis key 都设置了过期时间，但如果你的业务应用写入量很大，并且过期时间设置得比较久，那么短期间内 Redis 的内存依旧会快速增长。如果不控制会导致使用过多的内存资源。可以提前预估业务数据量，设置 maxmemory 控制实例的内存上限，避免 Redis 的内存持续膨胀。
    - 避免key集中过期，设置合理过期策略， 防止缓存雪崩。
    - 只使用DB0。
        - 在一个连接上操作多个 db 数据时，每次都需要先执行 SELECT，这会给 Redis 带来额外的压力
        - 使用多个 db 的目的是，按不同业务线存储数据，那为何不拆分多个实例存储呢？拆分多个实例部署，多个业务线不会互相影响，还能提高 Redis 的访问性能
        - Redis Cluster 只支持 db0，如果后期你想要迁移到 Redis Cluster，迁移成本高
    - 丢失数据不敏感，不开启AOF；必须开启，建议AOF everysec，且后台执行，但是依然可能有2秒数据丢失，刷盘IO负载过高导致影响主线程。
    - 关闭操作系统内存最大页机制。Redis在数据持久化的时候，fork一个子线程线程，共享内存，但是主线程需要修改数据，会采用写时复制，申请内存变大，会增加耗时，以及OOM问题。
    - 主从相同的max memory，时钟周期。
      Redis的过期删除策略是在启动时注册了serverCron函数，每一个时间时钟周期，都会抽取expires字典中的部分key进行清理，从而实现定期删除。
  - random key 可能会使redis发生抖动，主要是随机到要过期要删除的key。
  四、redis的源码学习
    Redis的数据结构部分
- 内存分配 zmalloc.c和zmalloc.h
- 动态字符串 sds.h和sds.c
- 双端链表 adlist.c和adlist.h
- 字典 dict.h和dict.c
- 跳跃表 server.h文件里面关于zskiplist结构和zskiplistNode结构，以及t_zset.c中所有zsl开头的函数，比如 zslCreate、zslInsert、zslDeleteNode等等。
- 基数统计 hyperloglog.c 中的 hllhdr 结构， 以及所有以 hll 开头的函数
  Redis的内存编码结构
- 整数集合数据结构 intset.h和intset.c
- 压缩列表数据结构 ziplist.h和ziplist.c
  Redis数据类型的实现
- 对象系统 object.c
- 字符串键 t_string.c
- 列表建 t_list.c
- 散列键 t_hash.c
- 集合键 t_set.c
- 有序集合键 t_zset.c中除 zsl 开头的函数之外的所有函数
- HyperLogLog键 hyperloglog.c中所有以pf开头的函数
  Redis数据库的实现
- 数据库实现 redis.h文件中的redisDb结构，以及db.c文件。
- 通知功能 notify.c
- RDB持久化 rdb.c
- AOF持久化 aof.c
- 发布和订阅 redis.h文件的pubsubPattern结构，以及pubsub.c文件。
- 事务 redis.h文件的multiState结构以及multiCmd结构，multi.c文件。
  Redis客户端和服务器端的代码实现
- 事件处理模块 ae.c/ae_epoll.c/ae_evport.c/ae_kqueue.c/ae_select.c
- 网路链接库 anet.c和networking.c
- 客户端 redis-cli.c
- lua脚本 scripting.c
- 慢查询 slowlog.c
- 监视 monitor.c
  Redis多机部分的代码实现
- 复制功能 replication.c
- Redis Sentinel sentinel.c
- 集群 cluster.c