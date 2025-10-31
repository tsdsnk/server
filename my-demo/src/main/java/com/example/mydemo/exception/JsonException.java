package com.example.mydemo.exception;

import com.example.mydemo.constant.ResponseStatus;

/**
 * 抛出异常并返回json格式数据
 * 详见异常处理 handler/ExceptionHandler
 */
public class JsonException extends BaseException {
    public JsonException(ResponseStatus responseStatus, Object details) {
        super(responseStatus, details);
    }

    public JsonException(Integer code, String message, Object details) {
        super(code, message,  details);
    }
}
