package com.example.mydemo.dao.impl.entity;

import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.vo.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminApplicationView {
    String accountName;
    String name;

    String ip;
    Integer port;

    Integer status;
    String applyAccountName;
    String applyPassword;
    String message;
    Date lastUpdate;

}
