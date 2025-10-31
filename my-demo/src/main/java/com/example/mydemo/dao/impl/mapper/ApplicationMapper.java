package com.example.mydemo.dao.impl.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.mydemo.dao.impl.entity.ApplicationEntity;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.Application;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<ApplicationEntity> {

    @Select("select * from (select a.application_id, a.account_name, acc.name, a.ip, h.port, " +
            "a.status, a.apply_account_name, a.apply_password, a.message, a.last_update " +
            "from t_application a " +
            "join t_account acc on a.account_name = acc.account_name " +
            "join t_host h on a.ip = h.ip) " +
            "tmp ${ew.customSqlSegment}")
    List<ApplicationEntity> selectList(@Param(Constants.WRAPPER) Wrapper<ApplicationEntity> wrapper);

    @Update("update t_application set status=#{status}, apply_account_name=#{applyAccountName}, apply_password=#{applyPassword}, message=#{message} where account_name=#{accountName} and ip=#{ip}")
    void updateApplication(@Param("ip") String ip, @Param("accountName") String accountName,
                           @Param("status") Integer status, @Param("applyAccountName") String applyAccountName, @Param("applyPassword") String applyPassword, @Param("message") String message) throws BaseException;


    @Select("select count(*) from  t_application where ip=#{ip} and status=#{status}")
    Integer cntApplicationOfHost(@Param("ip") String ip, @Param("status") Integer status);

    @Select("select count(*) from  t_application where account_name=#{accountName} and status=#{status}")
    Integer cntApplicationOfUser(@Param("accountName") String accountName, @Param("status") Integer status);

    @Delete("delete from t_application where account_name=#{accountName} and ip=#{ip}")
    void deleteApplication(@Param("accountName") String accountName, @Param("ip") String ip) throws BaseException;


}
