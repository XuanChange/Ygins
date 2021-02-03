package com.example.demo.test;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 一、使用NIO完成网络通信的三个核心；
 * <p>
 * 1.通道（channel）：负责连接
 * <p>
 * java.nio.channels.channel 接口：
 * | -- SelectableChannel
 * |-- SocketChannel
 * |-- ServerSocketChannel
 * |-- DatagramChannel
 * <p>
 * |Pipe.SinkChannel
 * |Pipe.SourceChannel
 * <p>
 * 2.缓冲区（Buffer）：负责数据的存取
 * <p>
 * 3.选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控SelectableChannel 的IO 状况
 */
public class TestNonBlockingNIO {

    //客户端
//    @Test
    public static void main(String[] args) throws IOException {
        //1.获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));

        //2.切换非阻塞模式
        socketChannel.configureBlocking(false);

        //3.分配指定缓冲区大小
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //4.发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        while (scanner.hasNext()) {
            String string = scanner.next();
            buffer.put((new Date().toString() + "\n" + string).getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }


        //5.关闭通道
        socketChannel.close();
    }

    //服务器
    @Test
    public void server() throws IOException {
        //1.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //2.切换非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9898));

        //4.获取选择器
        Selector selector = Selector.open();

        //5.将通道注册到选择器上

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //6.轮询式的获取选择器上已经准备的事件
        while (selector.select() > 0) {
            //7。获取当前选择器中所有注册的选择键
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                //8.获取准备就绪的事件
                SelectionKey selectionKey = iterator.next();

                //9.判断具体是什么事件准备就绪
                if (selectionKey.isAcceptable()) {
                    //10.若接受就绪 ，获取客户端连接
                    SocketChannel accept = serverSocketChannel.accept();
                    //11.切换非阻塞模式
                    accept.configureBlocking(false);

                    //12.将改通道注册到选择器

                    accept.register(selector, SelectionKey.OP_READ);

                } else if (selectionKey.isReadable()) {
                    //13.获取当前选择器上读就绪状态的通道
                    SocketChannel sChannel = (SocketChannel) selectionKey.channel();
                    //14.读数据
                    ByteBuffer allocate = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = sChannel.read(allocate)) > 0) {
                        allocate.flip();
                        System.out.println(new String(allocate.array(), 0, len));
                        allocate.clear();
                    }
                }
                //15.取消选择器
                iterator.remove();
            }
        }

    }

}
