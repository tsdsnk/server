package com.example.mydemo.dao.impl.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.mydemo.dao.impl.entity.HostEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface HostMapper extends BaseMapper<HostEntity> {
    @Select("select * from t_host where ip = #{ip}")
    HostEntity selectByIp(String ip);

    @Select("select port from t_host where ip = #{ip}")
    Integer selectPortByIp(String ip);

    @Update("update t_host set port=#{port}, name=#{name}, gpu_type=#{gpuType}, gpu_memory=#{gpuMemory}, gpu_number=#{gpuNumber}, cuda_version=#{cudaVersion}, description=#{description} where ip = #{ip}")
    void updateHostInfo(@Param("ip") String ip, @Param("port") Integer port, @Param("name") String name,
                    @Param("gpuType") String gpuType, @Param("gpuMemory") Integer gpuMemory, @Param("gpuNumber") Integer gpuNumber, @Param("cudaVersion") String cudaVersion,
                    @Param("description") String description);
    @Update("update t_host set admin_port=#{port}, admin_account_name=#{accountName}, password=#{password} where ip=#{ip}")
    void updateHostAdminAccount(@Param("ip") String ip, @Param("port") Integer port, @Param("accountName") String accountName, @Param("password") String password);

    List<HostEntity> selectList(@Param(Constants.WRAPPER) LambdaQueryWrapper<HostEntity> queryWrapper);

}
