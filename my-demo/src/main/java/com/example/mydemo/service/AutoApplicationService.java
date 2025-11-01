package com.example.mydemo.service;

import com.example.mydemo.exception.RejectException;
import com.example.mydemo.exception.UnprocessableException;
import com.example.mydemo.vo.Application;

public abstract class AutoApplicationService {

    public abstract void check(Application application) throws RejectException, UnprocessableException;
    public abstract void execApply(Application application) throws UnprocessableException;
    public abstract void execDelete(Application application) throws UnprocessableException;

    public void apply(Application application) throws UnprocessableException, RejectException {
        check(application);
        execApply(application);


    }

}
