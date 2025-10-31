package com.example.mydemo.dao;

import com.example.mydemo.constant.Role;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.filter.AccountFilter;

import java.util.List;

public interface AccountDao {
    void addAccount(Account account) throws BaseException;
    void updateAccount(Account account) throws BaseException;
    void deleteAccount(AccountFilter filter) throws BaseException;
    List<Account> select(AccountFilter filter) throws BaseException;
    Account select(String accountName) throws BaseException;
}
