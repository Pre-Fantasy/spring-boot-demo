package com.example.demo.core.ret;


/**
 * 返回实体对象
 */
public class RetResult<T> {

    public int code;

    private String msg;

    private T data;

    public RetResult<T> setCode(RetCode retCode) {
        this.code = retCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public RetResult<T> setCode (int code){
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RetResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public RetResult setData(T data) {
        this.data = data;
        return this;
    }
}
