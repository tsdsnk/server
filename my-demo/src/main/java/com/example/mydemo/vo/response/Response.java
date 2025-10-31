package com.example.mydemo.vo.response;

import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Integer code;
    private Object data;

    public static Response ofException(BaseException e) {
        return new Response(e.getCode(), e.getDetails());
    }

    public static Response ofSuccess(Object data) {
        return new Response(ResponseStatus.OK.getCode(), data);
    }

}
