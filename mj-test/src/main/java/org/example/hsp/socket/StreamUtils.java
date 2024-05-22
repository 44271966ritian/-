package org.example.hsp.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

    /*
    * TODO:可以把文件的内容读取到字节数组
    * */

    public static byte[] streamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//创建输出流对象
        byte[] b = new byte[1024];
        int len;
        while ((len=is.read(b))!=-1){
            bos.write(b,0,len);
        }
        byte[] array = bos.toByteArray();
        bos.close();
        return array;
    }

    public static String streamToString(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();
        int len;
        while ((len=is.read(b))!=-1){
            stringBuilder.append(new String(b,0,len));
        }
        return stringBuilder.toString();
    }


}
