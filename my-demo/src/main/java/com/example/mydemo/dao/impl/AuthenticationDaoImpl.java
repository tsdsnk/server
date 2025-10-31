package com.example.mydemo.dao.impl;

import com.example.mydemo.dao.AuthenticationDao;
import com.example.mydemo.dao.impl.entity.JwtEntity;
import com.example.mydemo.dao.impl.mapper.JwtMapper;
import com.example.mydemo.vo.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AuthenticationDaoImpl implements AuthenticationDao {

    @Autowired
    JwtMapper jwtMapper;

    @Override
    public void saveJwt(String accountName, String jwt, Date expireTime) {
        JwtEntity jwtEntity = jwtMapper.selectByAccountName(accountName);
        if (jwtEntity == null) {
            jwtEntity = new JwtEntity();
            jwtEntity.setAccountName(accountName);
            jwtEntity.setJwt(jwt);
            jwtEntity.setExpireTime(expireTime);
            jwtMapper.insert(jwtEntity);
        }else {
            jwtMapper.updateByAccountName(accountName, jwt, expireTime);
        }
    }

    @Override
    public Jwt getJwt(String accountName) {
        return jwtMapper.selectByAccountName(accountName).toJwt();
    }

    @Override
    public void deleteByAccountName(String accountName) {
        jwtMapper.deleteByAccountName(accountName);
    }

}
