package com.learning.notebook.tips.basic.io;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MappedByteBufferTest {

    public static void main(String[] args) throws Exception {

        /**
         * FileChannel不是非阻塞的，只有套接字和管道才能真正通过select()机制支持非阻塞I / O。
         *
         * MappedByteBuffer 使用是堆外的虚拟内存，因此分配（map）的内存大小不受 JVM 的 -Xmx 参数限制，但是也是有大小限制的。如果当文件超出 Integer.MAX_VALUE 字节限制时，可以通过 position 参数重新 map 文件后面的内容。
         * MappedByteBuffer 在处理大文件时性能的确很高，但也存内存占用、文件关闭不确定等问题，被其打开的文件只有在垃圾回收的才会被关闭，而且这个时间点是不确定的。
         * MappedByteBuffer 提供了文件映射内存的 mmap() 方法，也提供了释放映射内存的 unmap() 方法。然而 unmap() 是 FileChannelImpl 中的私有方法，无法直接显示调用。因此，用户程序需要通过 Java 反射的调用 sun.misc.Cleaner 类的 clean() 方法手动释放映射占用的内存区域。
         *
         * DirectByteBuffer 和零拷贝有什么关系？DirectByteBuffer 是 MappedByteBuffer 的具体实现类。
         * MappedByteBuffer 进行内存映射时，它的 map() 方法会通过 Util.newMappedByteBuffer() 方法通过反射机制获取 DirectByteBuffer 的构造器，然后创建一个 DirectByteBuffer 的实例。
         * 除了允许分配操作系统的直接内存以外，DirectByteBuffer 本身也具有文件内存映射的功能，这里不做过多说明。我们需要关注的是，DirectByteBuffer 在 MappedByteBuffer 的基础上提供了内存映像文件的随机读取 get() 和写入 write() 的操作。
         */

        /**
         * mmap
         */
        try (FileChannel outputChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)) {
            MappedByteBuffer mappedByteBuffer = outputChannel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            mappedByteBuffer.put(0, (byte) 'H');
            mappedByteBuffer.force();
            mappedByteBuffer.put(3, (byte) '9');
//            mappedByteBuffer.put(5, (byte) 'Y'); //IndexOutOfBoundsException
        }

        /**
         * sendfile
         */
        try (FileChannel inputChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
            FileChannel outputChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE)
        ) {
//            两种方式都行
//            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }
}
