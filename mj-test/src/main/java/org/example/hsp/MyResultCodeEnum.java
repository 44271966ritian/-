package org.example.hsp;


import lombok.Getter;

@Getter//提供获取属性的方法，属性值都是自己预设的，所以不需要set方法
public enum MyResultCodeEnum {

    SUCCESS(200,"成功返回"),
    ERROR(9999,"网络异常");

    //存在哪些属性？？？
    //状态码   状态消息
    private Integer code;
    private String message;

    private MyResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
