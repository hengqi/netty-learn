package com.chenhl.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

/*
    Netty ByteBuf所提供的3种缓冲区类型
    1. heap buffer
    2. direct buffer
    3. composite buffer 复合缓冲区

 */
public class ByteBufTest2 {

    public static void main(String[] args) {
        // 创建一个复合缓冲区
        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();

        ByteBuf heapBuf = Unpooled.buffer(10);
        ByteBuf directBuf = Unpooled.directBuffer(8);

        compositeByteBuf.addComponents(heapBuf, directBuf);
//        compositeByteBuf.removeComponent(0);

        Iterator<ByteBuf> iterator = compositeByteBuf.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        System.out.println("======");

        compositeByteBuf.forEach(System.out::println);
    }
}


/*

Heap Buffer(堆缓冲区)
这是最常用的类型，ByteBuf将数据存储到JVM中的堆空间中，并且将实际的数据存放到byte array中来实现
优点：由于数据存储在JVM的堆中，因此可以快速的创建与释放，并且它提供了直接访问内部字节数组的方法。
缺点：每次读取数据时，都需要先将数据复制到直接缓冲区中，再进行网络传输。

Direct Buffer(直接缓冲区)
在堆之外直接分配内存空间，直接缓冲区并不会占用堆的容量，因为它时由操作系统在本地内存进行的数据分配。
优点：在使用socket进行数据传递时，性能非常好。因为数据直接位于操作系统的本地内存中，所以不需要从JVM中将数据再复制到直接缓冲区中，性能很好。
缺点：因为Direct Buffer是直接在操作系统的内存中的，所以内存空间的分配与释放要比堆空间更加复杂，而且速度要慢一些。
Netty通过提供内存池来解决这个问题。直接缓冲区并不支持通过字节数组的方式来访问它里面存放的数据。

重点：对于后端的业务消息的编解码来说，推荐使用HeapByteBuffer；对于I/O通信线程在读写缓冲区时，推荐使用DirectByteBuf.


Composite Buffer(复合缓冲区)

=======

JDK的ByteBuffer与Netty的ByteBuf之间的差异比对：
1.Netty的ByteBuf采用了读写索引分离的策略（ridx/widx）,一个初始化的ByteBuf（尚未有任何数据）的ridx与widx值都为0.
2.当读索引与写索引处于同一个位置时，如果我们继续读取，那么就会抛出IndexOutOfBoundsException.
3.对于ByteBuf的任何读写操作都会分别的维护读索引与写索引。maxCapacity最大容量默认的限制就是Integer.MAX_VALUE

JDK的ByteBuffer缺点
1.因为ByteBuffer里维护这一个final类型的字节数组，也就是长度是固定的，一旦分配好后就不能扩容；而且当待存储的数据字节很大时很有可能出现IndexOutOfBoundsException。
如果要预防这个异常，那就需要在存储之前完全确定好待存储的字节大小。如果ByteBuffer的空间不足，我们只有创建一个全新的ByteBuffer对象，然后再将之前的ByteBuffer中的数据
复制过来，这都要由开发者手动完成。
2.ByteBuffer只使用一个position来标识位置信息，在进行读写切换时，就需要调用flip方法或是rewind方法，使用时不是很方便

Netty的ByteBuf的优点
1.存储字节的数组是动态的，其最大值默认是Integer.MAX_VALUE。这里的动态性是体现在write方法中的，write方法在执行时会判断buffer的容量，如果不足，则自动扩容。
2.ByteBuf的读写索引时完全分开的，使用起来也很方便。























 */
