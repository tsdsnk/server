package com.example.mydemo.dao.impl.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApplicationView {

    String ip;
    String accountName;

    Integer port;
    String gpuType;
    Integer gpuMemory;
    Integer gpuNumber;
    String cudaVersion;
    String description;

    Integer status;
    String applyAccountName;
    String applyPassword;
    String message;
    Date lastUpdate;

    Integer userNumber;
}
