package com.example.mydemo.configure.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;


@Data
@Component
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {
    /** 加密token */
    private String token;
    /** 过期时间 */
    private Long ttl;
    /** 开启 记住我 之后 jwt 过期时间 */
    private Long rememberMe;

    List<String> ignorePath;
}
