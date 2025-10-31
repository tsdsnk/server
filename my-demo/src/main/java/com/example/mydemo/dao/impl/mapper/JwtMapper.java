package com.example.mydemo.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydemo.dao.impl.entity.JwtEntity;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface JwtMapper extends BaseMapper<JwtEntity> {

    @Select("select * from t_jwt where account_name = #{accountName}")
    JwtEntity selectByAccountName(@Param("accountName") String accountName);


    @Update("update t_jwt set jwt=#{jwt}, expire_time=#{expireTime} where account_name=#{accountName}")
    void updateByAccountName(@Param("accountName") String accountName, @Param("jwt") String jwt, @Param("expireTime") Date expireTime);


    @Delete("delete from t_jwt where account_name = #{accountName}")
    void deleteByAccountName(@Param("accountName") String accountName);
}
