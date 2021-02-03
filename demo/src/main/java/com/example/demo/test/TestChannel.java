package com.example.demo.test;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;

public class TestChannel {

    @Test
    public void test6() throws CharacterCodingException {
        Charset charset = Charset.forName("GBK");

        //获取编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //获取解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("柴轩！");
        charBuffer.flip();

        //编码
        ByteBuffer buffer = charsetEncoder.encode(charBuffer);

        for (int i = 0; i < buffer.limit(); i++) {
            System.out.println(buffer.get());
        }
        //解码
        buffer.flip();
        CharBuffer charBuffer2 = charsetDecoder.decode(buffer);
        System.out.println(charBuffer2.toString());

        System.out.println("------------------------- -------------");

        Charset charset3 = Charset.forName("UTF-8");
        buffer.flip();
        CharBuffer buffer3 = charset3.decode(buffer);
        System.out.println(buffer3.toString());
    }

    @Test
    public void test5() {
        Map<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    @Test
    public void test4() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("1.txt", "rw");

        //1.获取通道
        FileChannel channel = randomAccessFile.getChannel();

        //2.分配指定缓冲区的大小
        ByteBuffer buffer1 = ByteBuffer.allocate(100);
        ByteBuffer buffer2 = ByteBuffer.allocate(1024);
        ByteBuffer[] byteBuffers = {buffer1, buffer2};
        channel.read(byteBuffers);

        //分散读取
        for (ByteBuffer byteBuffer : byteBuffers) {
            byteBuffer.flip();
        }
        System.out.println(new String(byteBuffers[0].array(), 0, byteBuffers[0].limit()));
        System.out.println("--------------------------");
        System.out.println(new String(byteBuffers[1].array(), 0, byteBuffers[1].limit()));

        //4.聚集写入
        RandomAccessFile randomAccessFile2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = randomAccessFile2.getChannel();

        channel2.write(byteBuffers);
    }

    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }

    @Test
    public void test2() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel inFileChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outFileChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        MappedByteBuffer in = inFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inFileChannel.size());
        MappedByteBuffer out = outFileChannel.map(FileChannel.MapMode.READ_WRITE, 0, inFileChannel.size());
        byte[] dst = new byte[in.limit()];
        in.get(dst);
        out.put(dst);
        inFileChannel.close();
        outFileChannel.close();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }


    @Test
    public void test1() {
        long start = System.currentTimeMillis();
        try {
            FileInputStream fis = new FileInputStream("1.jpg");
            FileOutputStream fos = new FileOutputStream("2.jpg");

            FileChannel inChannel = fis.getChannel();
            FileChannel outChannel = fos.getChannel();

            ByteBuffer buf = ByteBuffer.allocate(1024);

            while (inChannel.read(buf) != -1) {
                buf.flip();
                outChannel.write(buf);
                buf.clear();
            }
            outChannel.close();
            inChannel.close();
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

}
