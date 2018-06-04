package com.example.demo.core.ret;


import java.io.Serializable;

/**
 * 业务异常类
 */
public class ServiceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -4211251840097545755L;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
