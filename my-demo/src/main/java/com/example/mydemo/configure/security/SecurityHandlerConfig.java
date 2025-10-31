package com.example.mydemo.configure.security;

import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.vo.response.Response;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class SecurityHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) ->
                Response.ofException(new JsonException(ResponseStatus.UNAUTHORIZED, accessDeniedException.getMessage()));
    }

}