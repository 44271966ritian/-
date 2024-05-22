package org.example.hsp.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTCP04Client {

    public static void main(String[] args) throws IOException {

        //1.发出对本机8888端口的连接
        Socket socket = new Socket(InetAddress.getLocalHost(), 8888);

        //2.创建输入流，读取磁盘图片
        //文件路径
        String filePath = "D:\\1.jpg";
        //处理流
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath));
        //数组中就是文件内容
        byte[] bytes = StreamUtils.streamToByteArray(bis);

        //3.创建输出流，通过socket创建的
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(bytes);
        bis.close();
        socket.shutdownOutput();//设置写入数据的结束标志

        //4.读取返回的数据
        InputStream inputStream = socket.getInputStream();
        String s = StreamUtils.streamToString(inputStream);
        System.out.println(s);


        //关闭相关的流
        bos.close();
        socket.close();


    }

}
