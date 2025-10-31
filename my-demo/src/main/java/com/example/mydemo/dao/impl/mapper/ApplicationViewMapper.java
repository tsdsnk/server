package com.example.mydemo.dao.impl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mydemo.dao.impl.entity.AdminApplicationView;
import com.example.mydemo.vo.Application;
import com.example.mydemo.dao.impl.entity.UserApplicationView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ApplicationViewMapper extends BaseMapper<UserApplicationView> {
    @Select("select h.ip, h.port, h.gpu_type, h.gpu_memory, h.gpu_number, h.cuda_version, h.description, " +
            "a.account_name, a.ip, a.status, a.apply_account_name, a.apply_password, a.message, a.last_update, " +
            "a2.user_number " +
            "from t_host h " +
            "left join (select * from t_application where account_name=#{accountName} ) as a on a.ip = h.ip " +
            "left join (select ip, count(*) as user_number from t_application as acc2 where status=1 group by ip) as a2 on a.ip = a2.ip")
    /** status = 1: Approved */
    List<UserApplicationView> view(@Param("accountName") String accountName);

    @Select("select app.*, acc.name, h.port from t_application app " +
            "join t_account acc on app.account_name = acc.account_name " +
            "join t_host h on h.ip = app.ip " +
            "where app.account_name=#{accountName} and app.ip=#{ip} ")
    List<AdminApplicationView> selectApplication(@Param("accountName") String accountName, @Param("ip") String ip);

}
