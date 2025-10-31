package com.example.mydemo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.dao.HostDao;
import com.example.mydemo.dao.impl.entity.HostEntity;
import com.example.mydemo.dao.impl.mapper.AccountMapper;
import com.example.mydemo.dao.impl.mapper.HostMapper;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.utils.EncryptionUtils;
import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.HostAdminAccount;
import com.example.mydemo.vo.filter.HostAdminAccountFilter;
import com.example.mydemo.vo.filter.HostFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class HostDaoImpl implements HostDao{

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private EncryptionUtils encryptionUtils;


    private HostEntity encryptHostEntity(Host host, HostAdminAccount hostAdminAccount){

            if (!host.getIp().equals(hostAdminAccount.getIp())){
                throw new BaseException(ResponseStatus.BAD_REQUEST, "host ip and admin account ip should be same");
            }
            return new HostEntity(
                    host.getIp(),
                    host.getName(),
                    host.getPort(),
                    host.getGpuType(),
                    host.getGpuMemory(),
                    host.getGpuNumber(),
                    host.getCudaVersion(),
                    host.getDescription(),
                    hostAdminAccount.getPort(),
                    hostAdminAccount.getAccountName(),
                    encryptionUtils.reversibleEncry(hostAdminAccount.getPassword())
            );
    }
    private HostEntity decryptHostEntity(HostEntity hostEntity){
        hostEntity.setPassword(encryptionUtils.decrypt(hostEntity.getPassword()));
        return hostEntity;
    }




    @Override
    public void addHost(Host host, HostAdminAccount hostAdminAccount) throws BaseException {
        try{
            hostMapper.insert(encryptHostEntity(host, hostAdminAccount));
        }catch (Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteHostAndAccount(String ip) throws BaseException {
        try {
            hostMapper.deleteById(ip);
        }catch (Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Override
    public void updateHost(Host host) throws BaseException {
        try {
            hostMapper.updateHostInfo(
                    host.getIp(),
                    host.getPort(),
                    host.getName(),
                    host.getGpuType(),
                    host.getGpuMemory(),
                    host.getGpuNumber(),
                    host.getCudaVersion(),
                    host.getDescription()
            );
        }catch (Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Host selectHost(String ip) throws BaseException {
        try {
            return toHost(hostMapper.selectByIp(ip));
        }catch (Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public List<Host> selectHost(HostFilter filter) throws BaseException {
        try {
            return hostMapper.selectList(buildQueryWrapper(filter))
                    .stream().map(this::toHost).collect(Collectors.toList());
        }catch (Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Override
    public void updateHostAdminAccount(HostAdminAccount hostAdminAccount) throws BaseException {
        try {
            hostMapper.updateHostAdminAccount(
                    hostAdminAccount.getIp(),
                    hostAdminAccount.getPort(),
                    hostAdminAccount.getAccountName(),
                    encryptionUtils.reversibleEncry(hostAdminAccount.getPassword())
            );
        }catch (Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }


    @Override
    public List<HostAdminAccount> selectHostAdminAccount(HostAdminAccountFilter filter) throws BaseException {
        try {
            return hostMapper.selectList(buildQueryWrapper(filter))
                    .stream().map(this::toHostAdminAccount).collect(Collectors.toList());
        }catch (Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private LambdaQueryWrapper<HostEntity> buildQueryWrapper(HostFilter filter){
        LambdaQueryWrapper<HostEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(filter!=null){
            if(filter.getIp()!=null){
                queryWrapper.eq(HostEntity::getIp,filter.getIp());
            }
            if(filter.getName()!=null){
                queryWrapper.eq(HostEntity::getName,filter.getName());
            }
            if(filter.getPort()!=null){
                queryWrapper.eq(HostEntity::getPort,filter.getPort());
            }
        }
        return queryWrapper;
    }

    private LambdaQueryWrapper<HostEntity> buildQueryWrapper(HostAdminAccountFilter filter){
        LambdaQueryWrapper<HostEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(filter!=null){
            if(filter.getIp()!=null){
                queryWrapper.eq(HostEntity::getIp,filter.getIp());
            }
            if(filter.getPort()!=null){
                queryWrapper.eq(HostEntity::getPort,filter.getPort());
            }
        }
        return queryWrapper;
    }

    private Host toHost(HostEntity entity){
        return new Host(
                entity.getIp(),
                entity.getPort(),
                entity.getName(),
                entity.getGpuType(),
                entity.getGpuMemory(),
                entity.getGpuNumber(),
                entity.getCudaVersion(),
                entity.getDescription()
        );
    }

    private HostAdminAccount toHostAdminAccount(HostEntity entity){
        return new HostAdminAccount(
                entity.getIp(),
                entity.getAdminPort(),
                entity.getAdminAccountName(),
                encryptionUtils.decrypt(entity.getPassword())
        );
    }

}
