package org.example.hsp.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTCP03Server {

    /*
    * TODO:使用字符流完成本文内容的传输
    *  需要使用转换流
    * */

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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);


        //4.回发一条信息
        //获取输出流
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello,client 字符流");
        bufferedWriter.newLine();//插入一个换行符，表示写入的内容结束，注意，此时要求对方使用readLine方式读取
        bufferedWriter.flush();//如果使用的字符流，需要我们是手动刷新，否则数据不会写入

        //5.关闭各项连接
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();
        serverSocket.close();

        System.out.println("服务端退出");
    }

}
