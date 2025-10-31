package com.example.mydemo.vo;


import com.example.mydemo.constant.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    String accountName;
    String name;

    String ip;
    Integer port;

    ApplicationStatus status;
    String applyAccountName;
    String applyPassword;
    String message;
    Date lastUpdate;

}