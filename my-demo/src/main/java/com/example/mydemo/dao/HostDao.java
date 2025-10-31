package com.example.mydemo.dao;

import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.HostAdminAccount;
import com.example.mydemo.vo.filter.HostAdminAccountFilter;
import com.example.mydemo.vo.filter.HostFilter;

import java.util.List;

public interface HostDao {
    void addHost(Host host, HostAdminAccount hostAdminAccount) throws BaseException;
    void deleteHostAndAccount(String ip) throws BaseException;
    void updateHost(Host host) throws BaseException;
    Host selectHost(String ip) throws BaseException;
    List<Host> selectHost(HostFilter filter) throws BaseException;
    void updateHostAdminAccount(HostAdminAccount hostAdminAccount) throws BaseException;
    List<HostAdminAccount> selectHostAdminAccount(HostAdminAccountFilter filter) throws BaseException;
}
