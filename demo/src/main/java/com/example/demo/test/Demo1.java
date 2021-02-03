package com.example.demo.test;

import org.junit.Test;

import java.nio.ByteBuffer;

public class Demo1 {

    @Test
    public void test3() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer.isDirect());
    }

    @Test
    public void test2() {
        String str2 = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);
        buf.put(str2.getBytes());

        buf.flip();
        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println(buf.position());

        buf.mark();
        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());

        buf.reset();
        System.out.println(buf.position());

        if (buf.hasRemaining()) {
            System.out.println(buf.remaining());
        }
    }

    @Test
    public void test() {
        String str = "abcde";

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("----------------------------allovate()------------------------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.put(str.getBytes());
        System.out.println("----------------------------put()------------------------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        buffer.flip();
        System.out.println("----------------------------flip()------------------------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());


        //4.利用get（）获取缓冲区中的数据
        byte[] dst = new byte[buffer.limit()];
        buffer.get(dst);
        System.out.println("----------------------------get()------------------------------------");
        System.out.println(new String(dst, 0, dst.length));
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //5.rewind() :可重复读
        buffer.rewind();
        System.out.println("----------------------------rewind()------------------------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());
        //6.clear(); :清空缓冲区，但是缓冲区中的数据依然存在，但是处于被遗忘状态
        System.out.println("----------------------------clear()------------------------------------");
        System.out.println(buffer.position());
        System.out.println(buffer.limit());
        System.out.println(buffer.capacity());

        System.out.println((char) buffer.get());
    }
}
