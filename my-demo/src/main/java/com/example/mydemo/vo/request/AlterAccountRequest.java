package com.example.mydemo.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AlterAccountRequest {

    String accountName;
    @NotBlank @Size(min = 1, max = 32)
    String password;
    @NotBlank @Size(min = 1, max = 32)
    String name;
}
