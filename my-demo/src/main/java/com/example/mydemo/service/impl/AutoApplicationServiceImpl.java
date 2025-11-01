package com.example.mydemo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschUtil;
import com.example.mydemo.dao.HostDao;
import com.example.mydemo.exception.RejectException;
import com.example.mydemo.exception.UnprocessableException;
import com.example.mydemo.service.AutoApplicationService;
import com.example.mydemo.service.impl.strategy.Strategy;
import com.example.mydemo.vo.Application;
import com.example.mydemo.vo.HostAdminAccount;
import com.jcraft.jsch.Session;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class AutoApplicationServiceImpl extends AutoApplicationService {

    @Autowired
    private List<Strategy> strategies;
    @Autowired
    private HostDao hostDao;

    @Override
    public void check(Application application)  throws RejectException, UnprocessableException {
        for (Strategy strategy : strategies) {
            strategy.checkApply(application);
        }
    }

    @Override
    public void execApply(Application application) throws UnprocessableException {
//        try{
//            List<String> commands = new ArrayList<>();
//            commands.add(StrUtil.format("echo 'new accountName:{} password:{}'", application.getApplyAccountName(), application.getApplyPassword()));
//            ssh(getHostAdminAccount(application), commands);
//        }catch (Exception e){
//            throw new UnprocessableException(e.getMessage());
//        }
        throw new UnprocessableException("Unimplemented: com.expample.mydemo.service.impl.AutoApplicationServiceImpl: :execApply");
    }

    @Override
    public void execDelete(Application application) throws UnprocessableException {
        throw new UnprocessableException("Unimplemented: com.expample.mydemo.service.impl.AutoApplicationServiceImpl: :execDelete");
    }




    public HostAdminAccount getHostAdminAccount(Application application) {
        return hostDao.selectHostAdminAccount(application.getIp());
    }

    public List<String> ssh(HostAdminAccount hostAdminAccount, List<String> commands) {
        Session session = JschUtil.getSession(
                hostAdminAccount.getIp(),
                hostAdminAccount.getPort(),
                hostAdminAccount.getAccountName(),
                hostAdminAccount.getPassword()
        );
        try{
            List<String> execResult = new ArrayList<>(commands.size());
            for (String command : commands) {
                execResult.add(JschUtil.exec(session, command, Charset.defaultCharset()));
            }
            return execResult;
        }catch (Exception e){
            throw new UnprocessableException(e.getMessage());
        }finally {
            JschUtil.close(session);
        }
    }


}
