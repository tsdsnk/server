package com.example.mydemo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Host {
    String ip;
    Integer port;
    String name;
    String gpuType;
    Integer gpuMemory;
    Integer gpuNumber;
    String cudaVersion;
    String description;
}
