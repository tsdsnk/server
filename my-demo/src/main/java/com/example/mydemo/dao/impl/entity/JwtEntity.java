package com.example.mydemo.dao.impl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mydemo.vo.Jwt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_jwt")
public class JwtEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String accountName;
    private String jwt;
    private Date expireTime;

    public Jwt toJwt() {
        return new Jwt(accountName, jwt, expireTime);
    }
}
