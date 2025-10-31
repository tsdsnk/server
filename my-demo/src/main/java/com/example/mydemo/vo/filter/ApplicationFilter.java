package com.example.mydemo.vo.filter;

import com.example.mydemo.constant.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFilter {
    Date from;
    Date to;
    String accountName;
    String ip;
    ApplicationStatus status;
}
