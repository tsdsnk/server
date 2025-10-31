package com.example.mydemo.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum ApplicationStatus {

    APPLYING("APPLYING", 0),
    APPROVED("APPROVED", 1),
    REJECTED("REJECTED", 2),
    ;
    private static Map<Integer, ApplicationStatus> map = new HashMap<>();
    static {
        for(ApplicationStatus applicationStatus : ApplicationStatus.values()){
            map.put(applicationStatus.code, applicationStatus);
        }
    }


    ApplicationStatus(String status, Integer code){
        this.status = status;
        this.code = code;
    }
    final String status;
    @JsonIgnore
    final Integer code;


    public static ApplicationStatus findByCode(Integer code){
        return map.get(code);
    }

}
