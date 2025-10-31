package com.example.mydemo.constant;


import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ResponseStatus {

    OK(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    UNKNOWN_ERROR(500, "Internal Server Error");


    private final Integer code;
    private final String message;
    ResponseStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
