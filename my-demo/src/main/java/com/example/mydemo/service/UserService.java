package com.example.mydemo.service;

import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.request.UserApplyRequest;
import com.example.mydemo.vo.response.UserHostView;

import java.util.List;

public interface UserService {
    public List<UserHostView> getHostInfo(String userAccount) throws BaseException;
    public String apply(String userAccount, UserApplyRequest param) throws BaseException;
    public void deleteApplication(String userAccount, String ip) throws BaseException;
}
