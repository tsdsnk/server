package com.example.mydemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostAdminAccount {
    String ip;
    Integer port;
    String accountName;
    String password;
}