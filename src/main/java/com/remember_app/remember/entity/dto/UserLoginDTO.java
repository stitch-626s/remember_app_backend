package com.remember_app.remember.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginDTO {
    @JsonProperty("userAccount")
    private String userAccount;
    private String password;
}
