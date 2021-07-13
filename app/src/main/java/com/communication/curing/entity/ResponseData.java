package com.communication.curing.entity;

import java.io.Serializable;

/**
 * author : liguo
 * date : 2021/6/9 16:21
 * description :
 */
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 5213230387175987834L;

    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
