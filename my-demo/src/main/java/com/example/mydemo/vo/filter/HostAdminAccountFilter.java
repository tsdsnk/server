package com.example.mydemo.vo.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HostAdminAccountFilter {
    String ip;
    Integer port;
}
