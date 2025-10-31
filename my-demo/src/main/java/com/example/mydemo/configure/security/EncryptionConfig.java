package com.example.mydemo.configure.security;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import com.example.mydemo.utils.EncryptionUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

@Data
@Configuration
@ConfigurationProperties("application.encryption")
public class EncryptionConfig {

    private String secretKey;
    private String salt;

    @Bean
    public AES aes() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return SecureUtil.aes(keyBytes);
    }

}
