package org.example.hsp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCP01Server {

    public static void main(String[] args) throws IOException {

        //1.监听本机的9999端口，等待连接
        //细节，这句话要求9999端口没有被占用，否则会报异常
        //很容易理解，一个端口对应一个服务
        //细节，这个serverSocket 可以通过 accept() 返回多个socket【多个客户端连接服务器的并发】
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("服务端在9999端口监听，等待连接");

        //2.当客户端连接9999端口时，程序会返回socket对象
        //否则程序阻塞在这里
        Socket socket = serverSocket.accept();
        System.out.println("客户端 socket = " + socket.getClass());

        //3.通过socket获取输入流
        InputStream inputStream = socket.getInputStream();

        //4.io读取
        byte[] buf = new byte[1024];
        int readLen = 0;
        while ((readLen = inputStream.read(buf)) != -1){

            System.out.println(new String(buf,0,readLen));

        }

        //5.关闭socket和输入流以及serverSocket
        inputStream.close();
        socket.close();
        serverSocket.close();


        System.out.println("服务端关闭");

    }

}
