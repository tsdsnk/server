package com.example.mydemo.dao.impl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mydemo.vo.Application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_application")
public class ApplicationEntity {
    @TableId(type = IdType.AUTO)
    Integer applicationId;

    String accountName;
    @TableField(exist = false)
    String name;

    String ip;
    @TableField(exist = false)
    Integer port;

    Integer status;
    String applyAccountName;
    String applyPassword;
    String message;
    Date lastUpdate;

}
