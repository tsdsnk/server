package com.example.mydemo.controller;


import com.example.mydemo.constant.ResponseStatus;
import com.example.mydemo.constant.Role;
import com.example.mydemo.exception.BaseException;
import com.example.mydemo.exception.JsonException;
import com.example.mydemo.service.CommonService;
import com.example.mydemo.utils.JwtUtil;
import com.example.mydemo.vo.UserPrincipal;
import com.example.mydemo.vo.request.AlterAccountRequest;
import com.example.mydemo.vo.request.LoginRequest;
import com.example.mydemo.vo.response.LoginResponse;
import com.example.mydemo.vo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public Response login(@Valid @RequestBody LoginRequest loginRequest) throws BaseException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getAccountName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createJwt(loginRequest.getRememberMe(), authentication);
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        return Response.ofSuccess(new LoginResponse(jwt, user.getNickName(), user.getRole()));
    }

    @PostMapping("alter-info")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Response alterInfo(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody AlterAccountRequest alterAccountRequest, HttpServletRequest request) throws BaseException {
        if(alterAccountRequest.getAccountName()!= null && !userPrincipal.getUsername().equals(alterAccountRequest.getAccountName())){
            throw new JsonException(ResponseStatus.FORBIDDEN, "Can't alter other's account");
        }
        jwtUtil.invalidateJwt(request);
        commonService.updateAccount(userPrincipal.getUsername(), alterAccountRequest.getPassword(), alterAccountRequest.getName());
        return Response.ofSuccess("success");
    }


    @GetMapping("/logout")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER')")
    public Response logout(@AuthenticationPrincipal UserPrincipal userPrincipal, HttpServletRequest request) throws BaseException {
        jwtUtil.invalidateJwt(request);
        return Response.ofSuccess("success");
    }

}
