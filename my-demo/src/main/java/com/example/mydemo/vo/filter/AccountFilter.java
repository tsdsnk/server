package com.example.mydemo.vo.filter;

import com.example.mydemo.constant.Role;
import lombok.Data;

@Data
public class AccountFilter {
    private String accountName;
    private String name;
    private Role role;
}
