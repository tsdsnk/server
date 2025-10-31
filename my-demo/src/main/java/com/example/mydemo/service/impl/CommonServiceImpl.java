package com.example.mydemo.service.impl;

import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.constant.Role;
import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.service.CommonService;
import com.example.mydemo.vo.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    AccountDao accountDao;



    @Override
    public void updateAccount(String accountName, String password, Role role, String name) throws BaseException {
        Account account = accountDao.select(accountName);
        account.setRole(role);
        account.setName(name);
        account.setPassword(password);
        accountDao.updateAccount(account);
    }

    @Override
    public void updateAccount(String accountName, String password, String name) throws BaseException {
        Account account = accountDao.select(accountName);
        account.setName(name);
        account.setPassword(password);
        accountDao.updateAccount(account);
    }
}
