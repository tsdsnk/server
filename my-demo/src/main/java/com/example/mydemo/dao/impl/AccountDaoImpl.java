package com.example.mydemo.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.constant.Role;
import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.dao.impl.entity.AccountEntity;
import com.example.mydemo.dao.impl.mapper.AccountMapper;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.utils.EncryptionUtils;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.filter.AccountFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private EncryptionUtils encryptionUtils;

    private Account encryptAccount(Account account){
        account.setPassword(encryptionUtils.irreversibleEncry(account.getPassword()));
        return account;
    }

    @Override
    public void addAccount(Account account) throws BaseException {
        try{
            accountMapper.insert(new AccountEntity(encryptAccount(account)));
        }catch(Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void updateAccount(Account account) throws BaseException {
        try {
            accountMapper.updateById(new AccountEntity(encryptAccount(account)));
        }catch(Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void deleteAccount(AccountFilter filter) throws BaseException {
        try {
            accountMapper.delete(buildQueryWrapper(filter));
        }catch(Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public List<Account> select(AccountFilter filter) throws BaseException {
        try {
            return accountMapper.selectList(buildQueryWrapper(filter))
                    .stream().map(this::toVo).collect(Collectors.toList());
        }catch(Exception e){
            throw new BaseException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Account select(String accountName) throws BaseException {
        try{
            return toVo(accountMapper.selectByAccountName(accountName));
        }catch(Exception e){
            throw new JsonException(ResponseStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private LambdaQueryWrapper<AccountEntity> buildQueryWrapper(AccountFilter filter){
        LambdaQueryWrapper<AccountEntity> queryWrapper = new LambdaQueryWrapper<>();
        if(filter!=null){
            if(filter.getAccountName() != null && !filter.getAccountName().isEmpty()){
                queryWrapper.eq(AccountEntity::getAccountName, filter.getAccountName());
            }
            if(filter.getName() != null && !filter.getName().isEmpty()){
                queryWrapper.like(AccountEntity::getName, filter.getName());
            }
            if(filter.getRole() != null){
                queryWrapper.eq(AccountEntity::getRole, filter.getRole().getCode());
            }
        }
        return queryWrapper;
    }

    private Account toVo(AccountEntity accountEntity) {
        return new Account(
                accountEntity.getAccountName(),
                accountEntity.getPassword(),
                Role.findByCode(accountEntity.getRole()),
                accountEntity.getName()
        );
    }

}
