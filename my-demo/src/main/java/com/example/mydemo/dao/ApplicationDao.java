package com.example.mydemo.dao;

import com.example.mydemo.dao.impl.entity.UserApplicationView;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.Application;
import com.example.mydemo.vo.filter.ApplicationFilter;

import java.util.List;

public interface ApplicationDao {
    void addApplication(Application application) throws BaseException;
    void updateApplication(Application application) throws BaseException;
    Application selectApplication(String ip, String accountName) throws BaseException;
    List<Application> selectApplication(ApplicationFilter filter)  throws BaseException;
    void deleteApplication(String accountName, String ip) throws BaseException;
    Integer cntHostUser(String ip) throws BaseException;
    Integer cntUserApproved(String accountName) throws BaseException;
    Integer cntUserApplying(String accountName) throws BaseException;
    List<UserApplicationView> applicationView(String accountName) throws BaseException;
}
