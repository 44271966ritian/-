package org.example.hsp.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCP04Server {

    public static void main(String[] args) throws IOException {

        //1.监听端口8888
        ServerSocket serverSocket = new ServerSocket(8888);

        //2.等待连接，获取socket
        Socket socket = serverSocket.accept();

        //3.读取客户端发送的数据
        //通过socket得到输入流
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        byte[] bytes = StreamUtils.streamToByteArray(bis);//得到内容数组

        //4.输出流，写入到D盘
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\2.jpg"));
        bos.write(bytes);
        bos.close();

        //5.回复数据
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.write("收到图片");
        writer.flush();
        socket.shutdownOutput();

        //5.关闭相关的流
        writer.close();
        bis.close();
        serverSocket.close();
        socket.close();
    }

}
