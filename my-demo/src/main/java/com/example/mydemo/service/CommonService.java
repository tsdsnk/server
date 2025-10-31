package com.example.mydemo.service;

import com.example.mydemo.constant.Role;
import com.example.mydemo.exception.BaseException;

public interface CommonService {
    void updateAccount(String accountName, String password, Role role, String name) throws BaseException;
    void updateAccount(String accountName, String password, String name) throws BaseException;
}
