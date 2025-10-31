package com.example.mydemo.vo.response;

import com.example.mydemo.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    String jwt;
    String name;
    Role role;
}
