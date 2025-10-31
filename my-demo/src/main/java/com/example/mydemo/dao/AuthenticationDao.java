package com.example.mydemo.dao;

import com.example.mydemo.vo.Jwt;

import java.util.Date;

public interface AuthenticationDao {
    void saveJwt(String accountName, String jwt, Date expireTime);
    Jwt getJwt(String accountName);
    void deleteByAccountName(String accountName);
}
