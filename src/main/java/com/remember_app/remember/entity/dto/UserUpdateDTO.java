package com.remember_app.remember.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserUpdateDTO {
    /**
     * 用户 ID
     */
    private Integer userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户邮箱
     */
    private String userEmail;

    /**
     * 用户手机号
     */
    private String userPhoneNum;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户旧密码
     */
    private String oldPassword;

    /**
     * 用户头像
     */
    private String userAvatar;
}
