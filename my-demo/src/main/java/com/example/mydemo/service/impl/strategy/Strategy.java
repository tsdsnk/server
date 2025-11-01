package com.example.mydemo.service.impl.strategy;

import com.example.mydemo.exception.RejectException;
import com.example.mydemo.exception.UnprocessableException;
import com.example.mydemo.vo.Application;

public interface Strategy {

    /**
     *  @param application 申请信息
     *  @throws UnprocessableException 无法自动处理
     *  @throws RejectException 拒绝申请
     */
    void checkApply(Application application) throws UnprocessableException, RejectException;
}
