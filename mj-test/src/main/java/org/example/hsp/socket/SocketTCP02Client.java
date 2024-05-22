package org.example.hsp.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTCP02Client {

    public static void main(String[] args) throws IOException {
        //1.连接端口号，获得socket
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);

        //2.获取输出流
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("hello,server".getBytes());

        socket.shutdownOutput();

        //3.获取输入流
        InputStream inputStream = socket.getInputStream();
        byte[] bf = new byte[1024];
        int readLine = 0;
        while ((readLine=inputStream.read(bf))!=-1){
            System.out.println(new String(bf,0,readLine));
        }

        socket.shutdownInput();

        //4.关闭连接
        outputStream.close();
        inputStream.close();
        socket.close();
        System.out.println("客户端退出");
    }

}
