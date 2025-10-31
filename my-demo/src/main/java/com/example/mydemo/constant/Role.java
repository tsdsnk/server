package com.example.mydemo.constant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


public enum Role {
    ADMIN("ADMIN", 0),
    USER("USER", 1)
    ;
    private static Map<Integer, Role> map = new HashMap<>();
    static {
        for(Role role : Role.values()){
            map.put(role.code, role);
        }
    }

    Role(String role, Integer code){
        this.role = role;
        this.code = code;
    }
    @Getter
    private final String role;
    @JsonIgnore
    @Getter
    private final Integer code;



    public static Role findByCode(Integer code){
        return map.get(code);
    }
}
