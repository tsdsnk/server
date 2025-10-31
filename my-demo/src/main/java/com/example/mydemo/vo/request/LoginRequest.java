package com.example.mydemo.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String accountName;
    @NotBlank
    private String password;

    private Boolean rememberMe = false;
}
