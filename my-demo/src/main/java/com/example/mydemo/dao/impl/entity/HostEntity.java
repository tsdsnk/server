package com.example.mydemo.dao.impl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.HostAdminAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_host")
public class HostEntity {
    @TableId(type = IdType.INPUT)
    String ip;

    String name;
    Integer port;
    String gpuType;
    Integer gpuMemory;
    Integer gpuNumber;
    String cudaVersion;
    String description;
    Integer adminPort;
    String adminAccountName;
    String password;




}
