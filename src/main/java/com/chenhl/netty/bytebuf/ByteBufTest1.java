package com.chenhl.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

public class ByteBufTest1 {

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("陈hello world", Charset.forName("utf-8"));

        if (byteBuf.hasArray()) {// 表示堆上的缓冲
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes, Charset.forName("utf-8")));

            System.out.println(byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println("ridx: "+byteBuf.readerIndex());
            System.out.println("widx: "+byteBuf.writerIndex());
            System.out.println("capacity: "+byteBuf.capacity());

            int length = byteBuf.readableBytes();
            System.out.println("readableBytes: "+length);


            for (int i = 0; i < byteBuf.readableBytes(); i++) {
                System.out.println((char)byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));
            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));
        }
    }
}
