package com.example.mydemo.vo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApplyRequest {
    @NotBlank
    String ip;

    @NotBlank
    @Size(min = 1, max = 32)
    String accountName;

    @NotBlank
    @Size(min = 1, max = 32)
    String password;

    @Size(min = 0, max = 512)
    String message;
}
