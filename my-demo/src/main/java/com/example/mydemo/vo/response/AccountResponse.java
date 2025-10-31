package com.example.mydemo.vo.response;

import com.example.mydemo.constant.Role;
import com.example.mydemo.vo.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponse {
    private String accountName;
    private Role role;
    private String name;

    public AccountResponse(Account account){
        accountName = account.getAccountName();
        role = account.getRole();
        name = account.getName();
    }
}
