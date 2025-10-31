package com.example.mydemo.vo;

import com.example.mydemo.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String accountName;
    private String password;
    private Role role;
    private String name;
}
