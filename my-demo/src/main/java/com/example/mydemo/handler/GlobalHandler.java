package com.example.mydemo.handler;

import com.example.mydemo.exception.JsonException;
import com.example.mydemo.exception.PageException;
import com.example.mydemo.vo.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class GlobalHandler {

    @ExceptionHandler(JsonException.class)
    @ResponseBody
    public Response jsonExceptionHandler(JsonException exception){
        log.error(exception.toString());
        return Response.ofException(exception);
    }

    @ExceptionHandler(PageException.class)
    public ModelAndView pageExceptionHandler(PageException exception){
        log.error(exception.toString());
        ModelAndView view = new ModelAndView();
        view.addObject("code", exception.getCode());
        view.addObject("message", exception.getMessage());
        view.addObject("details", exception.getDetails());
        view.setViewName("error");
        return view;
    }

}
