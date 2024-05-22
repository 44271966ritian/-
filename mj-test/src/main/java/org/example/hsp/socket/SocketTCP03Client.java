package org.example.hsp.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class SocketTCP03Client {


    /*
    * TODO:使用字符流完成文本的传输
    *  需要使用转换流
    * */

    public static void main(String[] args) throws IOException {
        //1.连接端口号，获得socket
        Socket socket = new Socket(InetAddress.getLocalHost(), 9999);

        //2.获取输出流
        OutputStream outputStream = socket.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write("hello,server 字符流");
        bufferedWriter.newLine();//插入一个换行符，表示写入的内容结束，注意，此时要求对方使用readLine方式读取
        bufferedWriter.flush();//如果使用的字符流，需要我们是手动刷新，否则数据不会写入



        //3.获取输入流
        InputStream inputStream = socket.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        System.out.println(s);


        //4.关闭连接
        bufferedReader.close();
        bufferedWriter.close();
        socket.close();
        System.out.println("客户端退出");
    }

}
