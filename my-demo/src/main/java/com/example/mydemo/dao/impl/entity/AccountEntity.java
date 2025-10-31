package com.example.mydemo.dao.impl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.mydemo.constant.Role;
import com.example.mydemo.vo.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_account")
public class AccountEntity {
    @TableId(type = IdType.INPUT)
    private String accountName;

    private String password;
    private Integer role;
    private String name;

    public AccountEntity(Account account) {
        this.accountName = account.getAccountName();
        this.password = account.getPassword();
        this.role = account.getRole().getCode();
        this.name = account.getName();
    }

}
