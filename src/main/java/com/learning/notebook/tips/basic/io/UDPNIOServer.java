package com.learning.notebook.tips.basic.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class UDPNIOServer {

    public static void receive() throws IOException {
        //  1. 获取通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        //  2. 切换非阻塞模式
        datagramChannel.configureBlocking(false);
        //  3. 绑定连接
        datagramChannel.bind(new InetSocketAddress(9898));
        //  4. 获得选择器
        Selector selector = Selector.open();
        // 5. 将[通道ssChannel]注册到[选择器selector]上, 并且指定[监听接收事件SelectionKey.OP_ACCEPT]
        datagramChannel.register(selector, SelectionKey.OP_READ);
        // 6. 轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select() > 0) {
            // 7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                // 8. 获取准备“就绪”的事件
                SelectionKey sk = it.next();
                // 9. 判断具体是什么事件准备就绪
                if (sk.isReadable()) {
                    //  10. 分配指定大小的缓冲区
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    // 11. 接收数据
                    // receive()方法会把接收到的数据包的内容放到给定的Buffer中。
                    // 如果接收到的包中含有的数据比Buffer的容量要大的话，剩下的数据就会被静静地抛弃。
                    datagramChannel.receive(buf);
                    buf.flip();
                    System.out.println(new String(buf.array(), 0, buf.limit()));
                    buf.clear();
                }
            }
            it.remove();
        }
    }

    public static void main(String[] args) {
        try {
            receive();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
