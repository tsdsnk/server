package com.example.mydemo.service.impl;

import com.example.mydemo.service.AutoApplicationService;
import com.example.mydemo.vo.Application;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AutoApplicationServiceImpl extends AutoApplicationService {

    @Override
    public Application check(Application application) {
        return application;
    }

    @Override
    public void execApply(Application application) {
        return;
    }

    @Override
    public void execDelete(Application application) {
        return;
    }
}
