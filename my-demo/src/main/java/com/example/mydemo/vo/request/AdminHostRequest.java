package com.example.mydemo.vo.request;

import com.example.mydemo.vo.Host;
import com.example.mydemo.vo.HostAdminAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminHostRequest {
    @NotBlank @Pattern(regexp = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$|^(?:(?:(?:[0-9A-Fa-f]{1,4}:){7}[0-9A-Fa-f]{1,4})|(?:(?:[0-9A-Fa-f]{1,4}:){1,7}:)|(?:(?:[0-9A-Fa-f]{1,4}:){1,6}:[0-9A-Fa-f]{1,4})|(?:(?:[0-9A-Fa-f]{1,4}:){1,5}(?::[0-9A-Fa-f]{1,4}){1,2})|(?:(?:[0-9A-Fa-f]{1,4}:){1,4}(?::[0-9A-Fa-f]{1,4}){1,3})|(?:(?:[0-9A-Fa-f]{1,4}:){1,3}(?::[0-9A-Fa-f]{1,4}){1,4})|(?:(?:[0-9A-Fa-f]{1,4}:){1,2}(?::[0-9A-Fa-f]{1,4}){1,5})|(?:[0-9A-Fa-f]{1,4}:(?::[0-9A-Fa-f]{1,4}){1,6})|(?::(?::[0-9A-Fa-f]{1,4}){1,7}|:))$")
    String ip;

    @NotBlank @Size(min = 1, max = 32)
    String name;

    @NotBlank @Min(0) @Max(65535)
    Integer port;

    @NotBlank @Size(min = 1, max = 64)
    String gpuType;

    @NotBlank @Min(0)
    Integer gpuMemory;

    @NotBlank @Min(1)
    Integer gpuNumber;

    @NotBlank @Size(min = 1, max = 16)
    String cudaVersion;

    @Size(max = 512)
    String description;

    @NotBlank @Min(0) @Max(65535)
    Integer adminPort;

    @NotBlank @Size(min = 1, max = 32)
    String adminAccountName;

    @NotBlank @Size(min = 1, max = 32)
    String adminPassword;

    public Host toHost(){
        return new Host(ip, port, name, gpuType, gpuMemory, gpuNumber, cudaVersion, description);
    }

    public HostAdminAccount toHostAdminAccount(){
        return new HostAdminAccount(ip, adminPort, adminAccountName, adminPassword);
    }
}
