package com.znv.controller.exception;

/**
 * @author pengYongQiang
 * @date 2020/11/3 15:45
 */
public class CommonControllerException extends RuntimeException{
    public CommonControllerException(String message) {
        super(message);
    }

    public CommonControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
