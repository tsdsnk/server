package com.example.mydemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Jwt {
    private String accountName;
    private String jwt;
    private Date expireTime;
}
