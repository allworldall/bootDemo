package com.example.bootdemo.utils.exceptionUtil;

/**
 * 自定义异常类，本类用来对请求参数校验不通过时抛出
 */
public class ParamException extends RuntimeException {

    public ParamException() {
        super();
    }

    public ParamException(String message) {
        super(message);
    }
}
