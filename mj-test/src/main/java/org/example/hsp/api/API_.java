package org.example.hsp.api;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class API_ {
    public static void main(String[] args) throws UnknownHostException {

        //1.获取本机的InetAddress对象
        InetAddress localHost = InetAddress.getLocalHost();

        //输出的是主机名和ip地址
        //localHost = LAPTOP-BAC39084/192.168.137.1
        System.out.println("localHost = " + localHost);

        //2.根据指定的主机名 获取 InetAddress对象
        InetAddress host1 = InetAddress.getByName("LAPTOP-BAC39084");
        //host1 = LAPTOP-BAC39084/192.168.137.1 效果和上面是一样的
        System.out.println("host1 = " + host1);

        //3.根据域名获取一个InetAddress对象,比如 www.baidu.com 对应的Inet对象
        InetAddress host2 = InetAddress.getByName("www.baidu.com");
        //host2 = www.baidu.com/220.181.38.149
        System.out.println("host2 = " + host2);

        //4.通过 InetAddress对象 获取对应的地址
        String hostAddress = host2.getHostAddress();
        //hostAddress = 220.181.38.150
        System.out.println("hostAddress = " + hostAddress);

        //5.通过  InetAddress对象，获取主机名/域名
        String hostName = host2.getHostName();
        //hostName = www.baidu.com
        System.out.println("hostName = " + hostName);


    }
}
