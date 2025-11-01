package com.example.mydemo.service.impl;

import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.dao.ApplicationDao;
import com.example.mydemo.dao.HostDao;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.exception.UnprocessableException;
import com.example.mydemo.service.AdminService;
import com.example.mydemo.service.AutoApplicationService;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.Application;
import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.filter.AccountFilter;
import com.example.mydemo.vo.filter.ApplicationFilter;
import com.example.mydemo.vo.filter.HostFilter;
import com.example.mydemo.vo.request.AdminHostRequest;
import com.example.mydemo.vo.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private ApplicationDao applicationDao;
    @Autowired
    private HostDao hostDao;

    @Autowired
    private AutoApplicationService autoApplicationService;


    @Override
    public List<Application> getApplications(ApplicationFilter filter) throws BaseException {
        return applicationDao.selectApplication(filter);
    }

    @Override
    public void approve(String accountName, String ip) throws BaseException {
        try{
            Application application = applicationDao.selectApplication(ip, accountName);
            application.setStatus(ApplicationStatus.APPROVED);
            autoApplicationService.execApply(application);
            applicationDao.updateApplication(application);
        }catch (UnprocessableException e){
            throw new JsonException(ResponseStatus.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public void reject(String accountName, String ip) throws BaseException {
        Application application = applicationDao.selectApplication(ip, accountName);
        application.setStatus(ApplicationStatus.REJECTED);
        applicationDao.updateApplication(application);
    }

    @Override
    public void deleteApplication(String accountName, String ip) throws BaseException {
        try{
            Application application = applicationDao.selectApplication(ip, accountName);
            if(application.getStatus() == ApplicationStatus.APPROVED){
                autoApplicationService.execDelete(application);
            }
            applicationDao.deleteApplication(accountName, ip);
        }catch (UnprocessableException e){
            throw new JsonException(ResponseStatus.UNKNOWN_ERROR, e.getMessage());
        }
    }

    @Override
    public List<Host> getHosts(HostFilter hostFilter) throws BaseException {
        return hostDao.selectHost(hostFilter);
    }

    @Override
    public void addHost(AdminHostRequest newHostParam) throws BaseException {
        hostDao.addHost(newHostParam.toHost(), newHostParam.toHostAdminAccount());
    }

    @Override
    public void deleteHost(String ip) throws BaseException {
        hostDao.deleteHostAndAccount(ip);
    }

    @Override
    public void updateHost(Host host) throws BaseException {
        hostDao.updateHost(host);
    }

    @Override
    public void updateHost(AdminHostRequest host) throws BaseException {
        hostDao.updateHost(host.toHost());
        hostDao.updateHostAdminAccount(host.toHostAdminAccount());
    }

    @Override
    public List<AccountResponse> getAccounts(AccountFilter accountFilter) throws BaseException {
        return accountDao.select(accountFilter).stream().map(AccountResponse::new).collect(Collectors.toList());
    }

    @Override
    public void addAccount(Account account) throws BaseException {
        accountDao.addAccount(account);
    }

    @Override
    public void deleteAccount(String accountName) throws BaseException {
        AccountFilter filter = new AccountFilter();
        filter.setAccountName(accountName);
        accountDao.deleteAccount(filter);
    }

    @Override
    public void updateAccount(Account account) throws BaseException {
        accountDao.updateAccount(account);
    }
}
