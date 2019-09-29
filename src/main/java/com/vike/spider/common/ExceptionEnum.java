package com.vike.spider.common;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
public enum ExceptionEnum {

    /** 登录错误 */
    LOGIN_ERROR(10001,"用户名或密码错误");



    private int code;
    private String message;
    ExceptionEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
