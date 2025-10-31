package com.example.mydemo.service;

import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class AuthorizeService implements UserDetailsService {
    @Autowired
    private AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountDao.select(s);
        if (account == null) {
            throw new UsernameNotFoundException("Unknown user");
        }
        return new UserPrincipal(account);
    }
}
