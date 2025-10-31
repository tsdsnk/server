package com.example.mydemo.configure.security;

import cn.hutool.core.util.StrUtil;
import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.service.AuthorizeService;
import com.example.mydemo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private AuthorizeService authorizeService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(isIgnored(httpServletRequest)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        String jwt = jwtUtil.getJwt(httpServletRequest);
        if(jwt!=null){
            String accountName = jwtUtil.getAccountNameFromJWT(jwt);
            UserDetails userDetails = authorizeService.loadUserByUsername(accountName);
            UsernamePasswordAuthenticationToken authentication =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        throw new JsonException(ResponseStatus.UNAUTHORIZED, "Unauthorized");

    }

    private boolean isIgnored(HttpServletRequest httpServletRequest) {
        String path = httpServletRequest.getServletPath();
        for (String ignore: jwtConfig.getIgnorePath()) {
            if (path.equals(ignore)) {
                return true;
            }
        }
        return false;
    }



}
