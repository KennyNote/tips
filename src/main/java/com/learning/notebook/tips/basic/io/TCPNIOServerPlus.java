package com.learning.notebook.tips.basic.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TCPNIOServerPlus {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final int CAPACITY = 1024;

    private static final String IP = "127.0.0.1";

    private static final int PORT = 9898;

    private final Map<SocketAddress,Object> response = new HashMap<>();

    private final ServerSocketChannel ssChannel;

    // 多路复用器 linux(select poll epoll) unix(kqueue)，取决于不同操作系统对于IO多路复用的实现
    private final Selector selector;

    public TCPNIOServerPlus() throws IOException {
        // fd = file descriptor 文件描述符
        // serverSocketChannel 约等于listen fd4
        this.ssChannel = ServerSocketChannel.open();
        this.ssChannel.configureBlocking(false);
        this.ssChannel.bind(new InetSocketAddress(PORT));

        //这里如果是epoll的话，open -> epoll_create -> fd3
        this.selector = Selector.open();
        // 如果是select，poll：会在jvm里开启一个数组，将fd4放入。
        // 如果是epoll：epoll_ctl(fd3->selector, Add, fd4, EPOLLIN)
        this.ssChannel.register(this.selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * Reactor模型可将Reactor拆分为了mainReactor和subReactor。
     * mainReactor主要进行客户端连接的处理，处理完成之后将该连接交由subReactor以处理客户端的网络读写。
     * subReactor则是使用一个线程池来支撑的，其读写能力将会随着线程数的增多而大大增加。
     * 对于业务操作，这里也是使用一个线程池，而每个业务请求都只需要进行编解码和业务计算。通过这种方式，服务器的性能将会大大提升，在可见情况下，其基本上可以支持百万连接。
     *
     *
     * 多路复用IO的优缺点
     * 不用再使用多线程来进行IO处理了(包括操作系统内核IO管理模块和应用程序进程而言)。当然实际业务的处理中，应用程序进程还是可以引入线程池技术的。
     * 同一个端口可以处理多种协议，例如，使用ServerSocketChannel测的服务器端口监听，既可以处理TCP协议又可以处理UDP协议。
     * 操作系统级别的优化: 多路复用IO技术可以是操作系统级别在一个端口上能够同时接受多个客户端的IO事件。同时具有之前我们讲到的阻塞式同步IO和非阻塞式同步IO的所有特点。Selector的一部分作用更相当于“轮询代理器”。
     * 都是同步IO: 目前我们介绍的 阻塞式IO、非阻塞式IO甚至包括多路复用IO，这些都是基于操作系统级别对“同步IO”的实现。我们一直在说“同步IO”，一直都没有详细说，什么叫做“同步IO”。实际上一句话就可以说清楚: 只有上层(包括上层的某种代理机制)系统询问我是否有某个事件发生了，否则我不会主动告诉上层系统事件发生了。
     */
    public void server() throws IOException {
        // 如果是select，poll的话 就是调用内核的select(fd4)
        // 如果是epoll的话，调用epoll_wait。
        while (this.selector.select() > 0) {
            // 返回的有状态fd集合
            Iterator<SelectionKey> it = this.selector.selectedKeys().iterator();
            ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
            // 不管啥多路复用器 只能给出文件描述符的状态 还是得一个一个去处理读写。
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                // ⭐️ Reactor模型与传统IO模型最重要的区分在于，Reactor模型是以事件（accept、write、read）进行驱动的，其能够将接收客户端连接，网络读和网络写，以及业务计算进行拆分，从而极大的提升处理效率；
                // Reactor模型是异步非阻塞模型，工作线程在没有网络事件时可以处理其他的任务，而不用像传统IO那样必须阻塞等待。
                if (sk.isAcceptable()) {
                    // select,poll 因为没有在内核开启空间，那么在jvm中保存  和前边的fd4那个listen一起
                    // epoll的话由于内核有开辟空间，通过epoll_ctl把新的客户端fd注册到内核空间
                    accept();
                } else if (sk.isWritable()) {
                    // write逻辑
                    send(buffer, sk);
                } else if (sk.isReadable()) {
                    // read逻辑
                    // 一般来说这里需要io thread 异步的对事件进行处理, IO和处理上解耦
                    receive(buffer, sk);
                }
                it.remove();
            }
        }
    }

    public void accept() throws IOException {
        SocketChannel sChannel = this.ssChannel.accept();
        sChannel.configureBlocking(false);
        // 调用了register,又回到了epoll_ctl  比如这个客户端是fd6
        // 如果是select，poll：会在jvm里开启一个数组，把fd6放进去
        // 如果是epoll：epoll_ctl(fd3->selector, Add, fd6, EPOLLIN)
        sChannel.register(this.selector, SelectionKey.OP_READ);
        System.out.println("[ client : " + sChannel.getRemoteAddress() + " ] build connection from client success");
        response.put(sChannel.getRemoteAddress(),null);
    }

    public void receive(ByteBuffer buffer, SelectionKey sk) {
        SocketChannel sChannel = null;
        try {
            sChannel = (SocketChannel) sk.channel();
            int readLength = 0;
            while ((readLength = sChannel.read(buffer)) > 0) {
                String receiveResult = new String(buffer.array(), 0, readLength);
                System.out.println("[ server " + sChannel.getLocalAddress() + " ] from [ client " + sChannel.getRemoteAddress() + " ] receive content : " + receiveResult);
                response.put(sChannel.getRemoteAddress(),receiveResult);
                buffer.clear();
            }
            sChannel.register(this.selector, SelectionKey.OP_WRITE);
        } catch (IOException e) {
            try {
                System.out.println("[ client : " + sChannel.getRemoteAddress() + " ] close connection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            sk.cancel();
        }
    }

    public void send(ByteBuffer buffer, SelectionKey sk) {
        SocketChannel sChannel = null;
        try {
            sChannel = (SocketChannel) sk.channel();
            buffer.put((LocalDateTime.now().format(DATE_TIME_FORMATTER)  + " " + response.get(sChannel.getRemoteAddress()) + " has been received").getBytes());
            buffer.flip();
            int writeLength = sChannel.write(buffer);
            System.out.println("[ server " + sChannel.getLocalAddress() + " ] to [ client " + sChannel.getRemoteAddress() + " ] send content : " + new String(buffer.array(), 0, writeLength));
            buffer.clear();
            sChannel.register(this.selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            try {
                System.out.println("[ client : " + sChannel.getRemoteAddress() + " ] close connection");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            sk.cancel();
        }
    }


    public static void main(String[] args) {
        try {
            new TCPNIOServerPlus().server();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
