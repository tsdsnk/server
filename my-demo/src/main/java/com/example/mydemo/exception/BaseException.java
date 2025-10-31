package com.example.mydemo.exception;

import cn.hutool.core.util.StrUtil;
import com.example.mydemo.constant.ResponseStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {
    private Integer code;
    private String message;
    private Object details;

    public BaseException(ResponseStatus responseStatus, Object details) {
        super(responseStatus.getMessage());
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.details = details;
    }

    public BaseException(Integer code, String message, Object details) {
        super(message);
        this.code = code;
        this.message = message;
        this.details = details;
    }

    @Override
    public String toString() {
        return StrUtil.format("[{}]: code:{}, message:{}, details:{}",
                this.getClass().getSimpleName(),  code, message, details);
    }

}