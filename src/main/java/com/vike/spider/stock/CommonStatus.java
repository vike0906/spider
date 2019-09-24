package com.vike.spider.stock;

/**
 * @author: lsl
 * @createDate: 2019/9/24
 */
public enum CommonStatus {

    /** 正常状态 */
    NORMAL(1,"正常"),
    /** 删除状态 */
    DELETE(9,"删除");



    private int code;
    private String mean;
    CommonStatus(int code, String mean){
        this.code = code;
        this.mean = mean;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }}
