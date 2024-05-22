package org.example.hsp.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTCP01Client {

    public static void main(String[] args) throws IOException {

        //1.连接服务端（ip，端口号）
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost = " + localHost);
        //连接本机的9999端口，连接成功返回socket对象
        Socket socket = new Socket(localHost, 9999);
        System.out.println("服务端 socket = " + socket.getClass());

        //2.连接成功之后，通过socket获取输出流
        //该输出流和该socket相关联
        OutputStream outputStream = socket.getOutputStream();

        //3.通过输出流，写入数据到数据通道
        outputStream.write("hello,server".getBytes());

        //4.关闭流对象和socket，释放资源
        outputStream.close();
        socket.close();
        System.out.println("客户端退出");

    }

}
