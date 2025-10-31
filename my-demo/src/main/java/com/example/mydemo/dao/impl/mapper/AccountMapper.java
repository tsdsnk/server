package com.example.mydemo.dao.impl.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydemo.dao.impl.entity.AccountEntity;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountMapper extends BaseMapper<AccountEntity> {
    @Select("select * from t_account where account_name = #{accountName}")
    AccountEntity selectByAccountName(@Param("accountName") String accountName);

    @Select("select name from t_account where account_name = #{accountName}")
    String selectNameByAccountName(@Param("accountName") String accountName);

    List<AccountEntity> selectList(@Param(Constants.WRAPPER) LambdaQueryWrapper<AccountEntity> queryWrapper);
}
