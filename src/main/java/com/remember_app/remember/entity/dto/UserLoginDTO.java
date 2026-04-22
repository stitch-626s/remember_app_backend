package com.remember_app.remember.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginDTO {
    /**
     *  用户登录账号
     */
    private String userAccount;

    /**
     *  用户登录密码
     */
    private String password;
}
