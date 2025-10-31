package com.example.mydemo.service;

import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.vo.Application;

public abstract class AutoApplicationService {

    public abstract Application check(Application application);
    public abstract void execApply(Application application);
    public abstract void execDelete(Application application);

    public Application apply(Application application){
        application =  check(application);
        if(application.getStatus().equals(ApplicationStatus.APPROVED)){
            execApply(application);
        }
        return application;
    }

}
