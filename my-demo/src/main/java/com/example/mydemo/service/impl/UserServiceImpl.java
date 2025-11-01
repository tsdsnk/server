package com.example.mydemo.service.impl;

import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.dao.ApplicationDao;
import com.example.mydemo.dao.HostDao;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.exception.RejectException;
import com.example.mydemo.exception.UnprocessableException;
import com.example.mydemo.service.AutoApplicationService;
import com.example.mydemo.service.UserService;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.Application;
import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.filter.ApplicationFilter;
import com.example.mydemo.vo.request.UserApplyRequest;
import com.example.mydemo.vo.response.UserHostView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private HostDao hostDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AutoApplicationService autoApplicationService;



    @Override
    public List<UserHostView> getHostInfo(String userAccount) throws BaseException {
        return applicationDao.applicationView(userAccount).stream().map(UserHostView::new)
                .collect(Collectors.toList());
    }


    @Override
    public String apply(String userAccount, UserApplyRequest param) throws BaseException {
        Application application = applicationDao.selectApplication(param.getIp(), userAccount);
        String response = "success";
        if(application == null){
            Account account = accountDao.select(userAccount);
            Host host = hostDao.selectHost(param.getIp());
            application = new Application(userAccount, account.getName(), param.getIp(), host.getPort(),
                    ApplicationStatus.APPLYING, param.getAccountName(), param.getPassword(), param.getMessage(), null);

            try{
                autoApplicationService.apply(application);
                application.setStatus(ApplicationStatus.APPROVED);
            } catch (RejectException e) {
                application.setStatus(ApplicationStatus.REJECTED);
                response = "Reject: " + e.getMessage();
            } catch (UnprocessableException e) {
                application.setStatus(ApplicationStatus.APPLYING);
                response = "Unprocessable: " + e.getMessage();
            } finally {
                applicationDao.addApplication(application);
            }


        }else{
            if(application.getStatus().equals(ApplicationStatus.APPLYING)){
                return "Already Apply";
            }
            application.setStatus(ApplicationStatus.APPLYING);
            application.setApplyAccountName(param.getAccountName());
            application.setApplyPassword(param.getPassword());

            try{
                autoApplicationService.apply(application);
                application.setStatus(ApplicationStatus.APPROVED);
            } catch (RejectException e) {
                application.setStatus(ApplicationStatus.REJECTED);
                response = "Reject: " + e.getMessage();
            } catch (UnprocessableException e) {
                application.setStatus(ApplicationStatus.APPLYING);
                response = "Unprocessable: " + e.getMessage();
            } finally {
                applicationDao.updateApplication(application);
            }
        }
        return response;

    }

    @Override
    public void deleteApplication(String userAccount, String ip) throws BaseException {
        try {
            Application application = applicationDao.selectApplication(ip, userAccount);
            if(application.getStatus().equals(ApplicationStatus.APPROVED)){
                autoApplicationService.execDelete(application);
            }
            applicationDao.deleteApplication(userAccount, ip);
        }catch (UnprocessableException e){
            throw new JsonException(ResponseStatus.UNKNOWN_ERROR, "Failed to delete application: " + e.getMessage());
        }

    }


}
