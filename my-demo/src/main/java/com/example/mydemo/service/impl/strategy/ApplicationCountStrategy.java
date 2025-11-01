package com.example.mydemo.service.impl.strategy;


import com.example.mydemo.configure.StrategyConfig;
import com.example.mydemo.dao.ApplicationDao;
import com.example.mydemo.exception.RejectException;
import com.example.mydemo.exception.UnprocessableException;
import com.example.mydemo.vo.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCountStrategy implements Strategy {

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private StrategyConfig strategyConfig;


    @Override
    public void checkApply(Application application) throws UnprocessableException, RejectException {
        String accountName = application.getAccountName();
        Integer approvedCount = applicationDao.cntUserApproved(accountName);
        Integer applyingCount = applicationDao.cntUserApplying(accountName);

        if(applyingCount + approvedCount >= strategyConfig.getMaxApplicationStrategy()){
            throw new RejectException("Maximum number of user application ("+ strategyConfig.getMaxApplicationStrategy() +") has been reached");
        }
    }
}
