package com.example.mydemo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.dao.ApplicationDao;
import com.example.mydemo.dao.impl.entity.AdminApplicationView;
import com.example.mydemo.dao.impl.entity.ApplicationEntity;
import com.example.mydemo.dao.impl.entity.UserApplicationView;
import com.example.mydemo.dao.impl.mapper.ApplicationMapper;
import com.example.mydemo.dao.impl.mapper.ApplicationViewMapper;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.utils.EncryptionUtils;
import com.example.mydemo.vo.Application;
import com.example.mydemo.vo.filter.ApplicationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationDaoImpl implements ApplicationDao {

    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private ApplicationViewMapper applicationViewMapper;

    @Autowired
    private EncryptionUtils encryptUtils;

    private ApplicationEntity encryptApplication(Application application) {
        return new ApplicationEntity(
                null,
            application.getAccountName(),
            application.getName(),
            application.getIp(),
            application.getPort(),
            application.getStatus().getCode(),
            application.getApplyAccountName(),
            encryptUtils.reversibleEncry(application.getApplyPassword()),
            application.getMessage(),
            application.getLastUpdate()
        );
    }


    @Override
    public void addApplication(Application application) throws BaseException {
        try {
            applicationMapper.insert(encryptApplication(application));
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void updateApplication(Application application) throws BaseException {
        try {
            ApplicationEntity e = encryptApplication(application);
            applicationMapper.updateApplication(e.getIp(), e.getAccountName(), e.getStatus(),
                    e.getApplyAccountName(), e.getApplyPassword(), e.getMessage());
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Application selectApplication(String ip, String accountName) throws BaseException {
        try{
            List<AdminApplicationView> r = applicationViewMapper.selectApplication(accountName, ip);
            if(r.isEmpty()){
                return null;
            }
            return toApplication(r.get(0));
        } catch (Exception e) {
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }


    }

    @Override
    public List<Application> selectApplication(ApplicationFilter filter) throws BaseException {
        try {
            return applicationMapper.selectList(buildQueryWrapper(filter))
                    .stream().map(this::toApplication).collect(Collectors.toList());
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteApplication(String accountName, String ip) throws BaseException {
        try {
            applicationMapper.deleteApplication(accountName, ip);
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Integer cntHostUser(String ip) throws BaseException {
        try {
            return applicationMapper.cntApplicationOfHost(ip, ApplicationStatus.APPROVED.getCode());
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Integer cntUserApproved(String accountName) throws BaseException {
        try {
            return applicationMapper.cntApplicationOfUser(accountName, ApplicationStatus.APPROVED.getCode());
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Integer cntUserApplying(String accountName) throws BaseException {
        try {
            return applicationMapper.cntApplicationOfUser(accountName, ApplicationStatus.APPLYING.getCode());
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public List<UserApplicationView> applicationView(String accountName) throws BaseException {
        try{
            return applicationViewMapper.view(accountName).stream()
                    .peek(userApplicationView -> userApplicationView.setApplyPassword(encryptUtils.decrypt(userApplicationView.getApplyPassword())))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }


    private Application toApplication(ApplicationEntity entity) {
        return new Application(
                entity.getAccountName(),
                entity.getName(),
                entity.getIp(),
                entity.getPort(),
                ApplicationStatus.findByCode(entity.getStatus()),
                entity.getApplyAccountName(),
                encryptUtils.decrypt(entity.getApplyPassword()),
                entity.getMessage(),
                entity.getLastUpdate()
        );
    }

    private Application toApplication(AdminApplicationView view) {
        return new Application(
                view.getAccountName(),
                view.getName(),
                view.getIp(),
                view.getPort(),
                ApplicationStatus.findByCode(view.getStatus()),
                view.getApplyAccountName(),
                encryptUtils.decrypt(view.getApplyPassword()),
                view.getMessage(),
                view.getLastUpdate()
        );
    }

    private LambdaQueryWrapper<ApplicationEntity> buildQueryWrapper(ApplicationFilter filter){
        LambdaQueryWrapper<ApplicationEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(filter!=null){
            if(filter.getFrom()!=null){
                queryWrapper.ge(ApplicationEntity::getLastUpdate, filter.getFrom());
            }
            if(filter.getTo()!=null){
                queryWrapper.le(ApplicationEntity::getLastUpdate, filter.getTo());
            }
            if(filter.getStatus()!=null){
                queryWrapper.eq(ApplicationEntity::getStatus, filter.getStatus().getCode());
            }
            if(filter.getAccountName()!=null){
                queryWrapper.eq(ApplicationEntity::getAccountName, filter.getAccountName());
            }
            if(filter.getIp()!=null){
                queryWrapper.eq(ApplicationEntity::getIp, filter.getIp());
            }
        }
        return queryWrapper;
    }
}
