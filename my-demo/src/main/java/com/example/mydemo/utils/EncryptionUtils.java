package com.example.mydemo.utils;


import cn.hutool.crypto.symmetric.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;




@Component
public class EncryptionUtils {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AES aes;

    public String irreversibleEncry(String msg){
        if (msg==null){
            return null;
        }
        return passwordEncoder.encode(msg);
    }

    public String reversibleEncry(String msg){
        if (msg==null){
            return null;
        }
        return aes.encryptBase64(msg);
    }

    public String decrypt(String msg){
        if (msg==null){
            return null;
        }
        return aes.decryptStr(msg);
    }

}
