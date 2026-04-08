package com.remember_app.remember.common;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.remember_app.remember.controller.SecurityProperties;
import com.remember_app.remember.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {

    @Resource
    private SecurityProperties securityProperties;

    /**
     * 创建 Token
     */
    public String creatToken(User user) {
        HashMap<String, Object> payload = new HashMap<>();
        payload.put(JWT.ISSUED_AT, DateUtil.date());
        payload.put(JWT.EXPIRES_AT, new Date(System.currentTimeMillis() + securityProperties.getJwtExpire()));
        payload.put("userId", user.getUserId());
        payload.put("userAccount", user.getUserAccount());
        return JWTUtil.createToken(payload, securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 检验 Token 是否有效
     */
    public boolean validateToken(String token) {
        try{
            return JWTUtil.verify(token, securityProperties.getJwtSecret().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 解析 Token 获取用户 ID
     */
    public Integer getUserIdByToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        return Integer.parseInt(jwt.getPayload("userId").toString());
    }

}
