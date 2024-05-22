package org.example.hsp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCP02Server {
    public static void main(String[] args) throws IOException {
        //1.监听端口9999
        ServerSocket serverSocket = new ServerSocket(9999);

        System.out.println("正在等待连接");

        //2.获取连接的socket
        // 没有连接就一直阻塞
        //获得连接就返回socket对象
        Socket socket = serverSocket.accept();

        System.out.println("连接成功");

        //3.获取输入流，读取客户端发来的消息
        InputStream inputStream = socket.getInputStream();
        byte[] bf = new byte[1024];
        int readLine = 0;
        while ((readLine=inputStream.read(bf))!=-1){
            System.out.println(new String(bf,0,readLine));
        }

        socket.shutdownInput();

        //4.回发一条信息
        //获取输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello,client".getBytes());

        socket.shutdownOutput();

        System.out.println("成功回送");

        //5.关闭各项连接
        inputStream.close();
        outputStream.close();
        socket.close();
        serverSocket.close();

        System.out.println("服务端退出");
    }

}
