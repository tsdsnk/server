package com.example.mydemo.controller;

import com.example.mydemo.exception.BaseException;
import com.example.mydemo.service.UserService;
import com.example.mydemo.vo.UserPrincipal;
import com.example.mydemo.vo.request.UserApplyRequest;
import com.example.mydemo.vo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Response userInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) throws BaseException {
        return Response.ofSuccess(userService.getHostInfo(userPrincipal.getUsername()));
    }

    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Response apply(@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody UserApplyRequest param) throws BaseException {
        return Response.ofSuccess(userService.apply(userPrincipal.getUsername(), param));
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Response delete(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String ip) throws BaseException {
        userService.deleteApplication(userPrincipal.getUsername(), ip);
        return Response.ofSuccess("success");
    }

}
