package com.remember_app.remember.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRegisterDTO {
    /**
     * 用户注册账号
     */
    private String userAccount;

    /**
     * 用户注册密码
     */
    private String password;
}
