package com.chenhl.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

public class OldIOClient {

    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("localhost", 8899);

        String fileName = "d:\\mybook\\《Java核心技术 卷1 基础知识（原书第9版）》（完整中文版）.pdf";

        InputStream is = new FileInputStream(fileName);

        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[4096];

        long readCount;
        long total = 0;

        long startTime = System.currentTimeMillis();

        while ((readCount = is.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }

        System.out.println("发送的字节数：" + total + ", 耗时：" + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        is.close();
    }
}
