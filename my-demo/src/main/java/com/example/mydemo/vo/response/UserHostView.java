package com.example.mydemo.vo.response;

import com.example.mydemo.constant.ApplicationStatus;
import com.example.mydemo.dao.impl.entity.UserApplicationView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHostView {

    String ip;
    String accountName;

    Integer port;
    String gpuType;
    Integer gpuMemory;
    Integer gpuNumber;
    String cudaVersion;
    String description;

    ApplicationStatus status;
    String applyAccountName;
    String applyPassword;
    String message;
    Date lastUpdate;

    Integer userNumber;

    public UserHostView(UserApplicationView applicationView) {
        this.ip = applicationView.getIp();
        this.accountName = applicationView.getAccountName();
        this.port = applicationView.getPort();
        this.gpuType = applicationView.getGpuType();
        this.gpuMemory = applicationView.getGpuMemory();
        this.gpuNumber = applicationView.getGpuNumber();
        this.cudaVersion = applicationView.getCudaVersion();
        this.description = applicationView.getDescription();
        this.status = ApplicationStatus.findByCode(applicationView.getStatus());
        this.applyAccountName = applicationView.getApplyAccountName();
        this.applyPassword = applicationView.getApplyPassword();
        this.message = applicationView.getMessage();
        this.lastUpdate = applicationView.getLastUpdate();
        this.userNumber = applicationView.getUserNumber();
    }

}
