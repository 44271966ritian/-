package com.atguigu.spzx.common.exception;


import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@SuppressWarnings({"ALL"})
@Data
public class GuiguException extends RuntimeException{
    private Integer code;//状态码
    private String message;

    private ResultCodeEnum resultCodeEnum;

    public GuiguException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum=resultCodeEnum;
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }
}
