package com.learning.notebook.tips.basic.io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TCPNIOClientPlus {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final int CAPACITY = 1024;
    private static final String IP = "127.0.0.1";
    private static final int PORT = 9898;
    private final SocketChannel sChannel;

    public TCPNIOClientPlus() throws IOException {
        this.sChannel = SocketChannel.open();
        this.sChannel.configureBlocking(false);
        this.sChannel.connect(new InetSocketAddress(IP, PORT));
    }

    public void client() throws IOException {
        if (this.sChannel.finishConnect()) {
            System.out.println("[ client : " + sChannel.getLocalAddress() + " ] build connection to server success");
            ByteBuffer buffer = ByteBuffer.allocate(CAPACITY);
            Scanner scan = new Scanner(System.in);
            while (scan.hasNext()) {
                try {
                    String content = scan.nextLine();
                    send(content, buffer, this.sChannel);
                    Thread.sleep(50);
                    receive(buffer, this.sChannel);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        this.sChannel.close();
    }

    public void receive(ByteBuffer buffer, SocketChannel sChannel) {
        try {
            int readLength;
            while ((readLength = sChannel.read(buffer)) > 0) {
                System.out.println("[ client " + sChannel.getLocalAddress() + " ] from [ server " + sChannel.getRemoteAddress() + " ] receive content : " + new String(buffer.array(), 0, readLength));
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String content, ByteBuffer buffer, SocketChannel sChannel) {
        try {
            byte[] bytes = (LocalDateTime.now().format(DATE_TIME_FORMATTER) + " " + content).getBytes();
            buffer.put(bytes);
            buffer.flip();
            int writeLength = sChannel.write(buffer);
            System.out.println("[ client " + sChannel.getLocalAddress() + " ] to [ server " + sChannel.getRemoteAddress() + " ] send content : " + new String(buffer.array(), 0, writeLength));
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new TCPNIOClientPlus().client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
