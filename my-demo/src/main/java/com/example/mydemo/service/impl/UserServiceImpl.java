package com.example.mydemo.service.impl;

import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.dao.ApplicationDao;
import com.example.mydemo.dao.HostDao;
import com.example.mydemo.exception.BaseException;
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
        /** 按理来说 使用人数 应该也放在mapper里由数据库查询，但懒了嘻嘻 */
        return applicationDao.applicationView(userAccount).stream().map(UserHostView::new)
                .collect(Collectors.toList());
    }

    @Override
    public void apply(String userAccount, UserApplyRequest param) throws BaseException {
        Application application = applicationDao.selectApplication(param.getIp(), userAccount);
        if(application == null){
            Account account = accountDao.select(userAccount);
            Host host = hostDao.selectHost(param.getIp());
            application = new Application(userAccount, account.getName(), param.getIp(), host.getPort(),
                    ApplicationStatus.APPLYING, param.getAccountName(), param.getPassword(), param.getMessage(), null);
            applicationDao.addApplication(autoApplicationService.apply(application));
        }else{
            application.setStatus(ApplicationStatus.APPLYING);
            application.setApplyAccountName(param.getAccountName());
            application.setApplyPassword(param.getPassword());
            applicationDao.updateApplication(autoApplicationService.apply(application));
        }

    }

    @Override
    public void deleteApplication(String userAccount, String ip) throws BaseException {
        applicationDao.deleteApplication(userAccount, ip);
    }

}
