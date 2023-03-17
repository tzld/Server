package com.example.server.enums;

public enum ResultEnum {
//    四个实例
    SUCCESS(0,"操作成功"),
        ERROR_UNKNOWN(-1,"未知错误"),
            ERROR_NOT_FOUND(1,"资源未找到"),
                ERROR_OPERATION(2,"操作不成功");

    int code;
    String msg;

//    构造方法
    ResultEnum(int code,String msg){
        this.code=code;
        this.msg=msg;
    }
//    普通方法
    public Integer getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
