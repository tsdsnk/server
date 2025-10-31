package com.example.mydemo.controller;

import com.example.mydemo.exception.BaseException;
import com.example.mydemo.service.AdminService;
import com.example.mydemo.vo.Account;
import com.example.mydemo.vo.filter.AccountFilter;
import com.example.mydemo.vo.filter.ApplicationFilter;
import com.example.mydemo.vo.filter.HostFilter;
import com.example.mydemo.vo.request.AdminHostRequest;
import com.example.mydemo.vo.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/application/info")
    public Response applicationInfo(@RequestBody ApplicationFilter filter) throws BaseException {
        return Response.ofSuccess(adminService.getApplications(filter));
    }

    @PostMapping("/application/approve")
    public Response approve(@RequestParam String accountName,  @RequestParam String ip) throws BaseException {
        adminService.approve(accountName, ip);
        return Response.ofSuccess("success");
    }

    @PostMapping("/application/reject")
    public Response reject(@RequestParam String accountName,  @RequestParam String ip) throws BaseException {
        adminService.reject(accountName, ip);
        return Response.ofSuccess("success");
    }

    @PostMapping("/application/delete")
    public Response deleteApplication(@RequestParam String accountName, @RequestParam String ip) throws BaseException {
        adminService.deleteApplication(accountName, ip);
        return Response.ofSuccess("success");
    }

    @PostMapping("/host/info")
    public Response hostInfo(@RequestBody HostFilter filter) throws BaseException {
        return Response.ofSuccess(adminService.getHosts(filter));
    }

    @PostMapping("/host/add")
    public Response addHost(@RequestBody AdminHostRequest newHostParam) throws BaseException {
        adminService.addHost(newHostParam);
        return Response.ofSuccess("success");
    }

    @PostMapping("/host/delete")
    public Response deleteHost(@RequestParam String ip) throws BaseException {
        adminService.deleteHost(ip);
        return Response.ofSuccess("success");
    }

    @PostMapping("/host/update")
    public Response updateHost(@RequestBody AdminHostRequest host) throws BaseException {
        adminService.updateHost(host);
        return Response.ofSuccess("success");
    }

    @PostMapping("/account/info")
    public Response accountInfo(@RequestBody AccountFilter filter) throws BaseException {
        return Response.ofSuccess(adminService.getAccounts(filter));
    }

    @PostMapping("/account/delete")
    public Response deleteAccount(@RequestParam String accountName) throws BaseException {
        adminService.deleteAccount(accountName);
        return Response.ofSuccess("success");
    }

    @PostMapping("/account/add")
    public Response addAccount(@RequestBody Account account) throws BaseException {
        adminService.addAccount(account);
        return Response.ofSuccess("success");
    }

    @PostMapping("/account/update")
    public Response updateAccount(@RequestBody Account account) throws BaseException {
        adminService.updateAccount(account);
        return Response.ofSuccess("success");
    }

}
