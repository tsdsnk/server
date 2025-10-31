package com.example.mydemo.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.mydemo.configure.security.JwtConfig;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.constant.Role;
import com.example.mydemo.dao.AuthenticationDao;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.vo.Jwt;
import com.example.mydemo.vo.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;

@Service
@Slf4j
@EnableConfigurationProperties(JwtConfig.class)
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private AuthenticationDao dao;


    public String createJwt(Boolean rememberMe, String id, String subject, Role role, Collection<? extends GrantedAuthority> authorities){
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getToken())
                .claim("role", role.getRole())
                .claim("authorities", authorities);
        Long ttl = rememberMe ? jwtConfig.getRememberMe() : jwtConfig.getTtl();
        Date expiration = DateUtil.offsetMillisecond(now, ttl.intValue());
        builder.setExpiration(expiration);

        String jwt = builder.compact();
        dao.saveJwt(id, jwt, expiration);
        return jwt;
    }

    public String createJwt(Boolean rememberMe, Authentication authentication) {
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        return createJwt(rememberMe, user.getUsername(), user.getPassword(), user.getRole(), user.getAuthorities());
    }

    public String getJwt(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Claims parseJwt(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.getToken()).parseClaimsJws(jwt).getBody();
        Jwt voJwt = dao.getJwt(claims.getId());
        if (voJwt == null) {
            throw new JsonException(ResponseStatus.UNAUTHORIZED, "Unauthorized");
        }
        if(voJwt.getExpireTime().before(new Date())){
            throw new JsonException(ResponseStatus.UNAUTHORIZED, "Authorization expired");
        }
        if(!jwt.equals(voJwt.getJwt())){
            throw new JsonException(ResponseStatus.UNAUTHORIZED, "Token invalid");
        }
        return claims;
    }

    public void invalidateJwt(String jwt) {
        dao.deleteByAccountName(getAccountNameFromJWT(jwt));
    }

    public void invalidateJwt(HttpServletRequest request) {
        invalidateJwt(getJwt(request));
    }

    public String getAccountNameFromJWT(String jwt) {
        Claims claims = parseJwt(jwt);
        return claims.getId();
    }



}
