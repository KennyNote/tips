package com.learning.notebook.tips.basic.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Scanner;

public class UDPNIOClient {

    public static void send() throws IOException {
        // 1. 获取通道
        DatagramChannel datagramChannel = DatagramChannel.open();
        // 2. 切换非阻塞模式
        datagramChannel.configureBlocking(false);
        // 3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4. 发送数据给服务端
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()) {
            String str = scan.next();
            buf.put((new Date().toString() + ":\n" + str).getBytes());
            buf.flip();
            datagramChannel.send(buf, new InetSocketAddress("127.0.0.1", 9898));
            buf.clear();
        }
        // 5. 关闭通道
        datagramChannel.close();
    }

    public static void main(String[] args) {
        try {
            send();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
