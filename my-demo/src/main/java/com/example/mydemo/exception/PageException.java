package com.example.mydemo.exception;

import com.example.mydemo.constant.ResponseStatus;

/**
 * 抛出异常并返回异常界面
 * 详见异常处理 handler/ExceptionHandler
 */
public class PageException extends BaseException {

    public PageException(ResponseStatus responseStatus, Object details) {
        super(responseStatus, details);
    }

    public PageException(Integer code, String message, Object details) {
        super(code, message, details);
    }
}
