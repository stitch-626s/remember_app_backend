package com.remember_app.remember.controller;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "remember.security")
public class SecurityProperties {

    private String rsaPrivateKey;
    private String jwtSecret;
    private Long jwtExpire;

}
