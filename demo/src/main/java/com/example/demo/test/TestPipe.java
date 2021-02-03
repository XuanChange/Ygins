package com.example.demo.test;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

public class TestPipe {

    @Test
    public void test1() throws IOException {
        //1.获取通道
        Pipe pipe = Pipe.open();

        //2.将缓冲区的数据写入管道

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sink = pipe.sink();
        buffer.put("通过单项管道发送数据".getBytes());
        buffer.flip();
        sink.write(buffer);

        //3.读取缓冲区中的数据
        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer.flip();
        int read = sourceChannel.read(buffer);
        System.out.println(new String(buffer.array(), 0, read));

        sourceChannel.close();
        sourceChannel.close();
    }
}
