package com.example.mydemo.service;

import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.Application;
import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.filter.AccountFilter;
import com.example.mydemo.vo.filter.ApplicationFilter;
import com.example.mydemo.vo.filter.HostFilter;
import com.example.mydemo.vo.request.AdminHostRequest;
import com.example.mydemo.vo.response.AccountResponse;

import java.util.List;

public interface AdminService {

    List<Application> getApplications(ApplicationFilter filter) throws BaseException;
    void approve(String accountName, String ip) throws BaseException;
    void reject(String accountName, String ip) throws BaseException;
    void deleteApplication(String accountName, String ip) throws BaseException;

    List<Host> getHosts(HostFilter hostFilter) throws BaseException;
    void addHost(AdminHostRequest newHostParam) throws BaseException;
    void deleteHost(String ip) throws BaseException;
    void updateHost(Host host) throws BaseException;
    void updateHost(AdminHostRequest host) throws BaseException;

    List<AccountResponse> getAccounts(AccountFilter accountFilter) throws BaseException;
    void addAccount(Account account) throws BaseException;
    void deleteAccount(String accountName) throws BaseException;
    void updateAccount(Account account) throws BaseException;
}
