package com.chenhl.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/*
    注意：通过索引来访问byte时并不会改变真实的读索引与写索引；我们可以通过ByteBuf的readerIndex()与writeInder()方法分别来直接修改读索引与写索引。
 */
public class ByteBufTest0 {

    public static void main(String[] args) {

        ByteBuf byteBuf = Unpooled.buffer(10);

        for (int i = 0; i < 10; i++) {
            //相对的写，每次writeIndex会加1
            byteBuf.writeByte(i);
        }

        for (int i = 0; i < byteBuf.capacity(); i++) {
            //绝对的读，不会改变readIndex的值
            System.out.println(byteBuf.getByte(i));
            System.out.println("===========");
            //相对的读
            System.out.println(byteBuf.readByte());
        }
    }
}
