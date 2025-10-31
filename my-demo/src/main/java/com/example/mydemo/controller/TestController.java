package com.example.mydemo.controller;

import com.example.mydemo.dao.AccountDao;
import com.example.mydemo.service.CommonService;
import com.example.mydemo.utils.JwtUtil;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.UserPrincipal;
import com.example.mydemo.vo.request.LoginRequest;
import com.example.mydemo.vo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AccountDao accountDao;

    @GetMapping("/login")
    public Response login() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccountName("admin");
        loginRequest.setPassword("123456");
        loginRequest.setRememberMe(true);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getAccountName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createJwt(loginRequest.getRememberMe(), authentication);
        return Response.ofSuccess(jwt);
    }

    @GetMapping("/a")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Response a(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.ofSuccess(userPrincipal);
    }


    @GetMapping("encode")
    public Response test() {
        return Response.ofSuccess(passwordEncoder.encode("123456"));
    }
}
