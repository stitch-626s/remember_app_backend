package com.remember_app.remember.common;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class RsaEncryptInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {

        String requestUri = request.getRequestURI();
        if (!requestUri.startsWith("/users")) {
            return;
        }

        if (response.isCommitted()) {
            return;
        }

        CharResponseWrapper responseWrapper = (CharResponseWrapper) response;
        String originalBody = responseWrapper.toString();

        if (originalBody == null || originalBody.isEmpty()) {
            return;
        }

        try {
            JSONObject jsonResponse = JSON.parseObject(originalBody);

            if (jsonResponse.containsKey("data") && jsonResponse.get("data") != null) {
                String aesKey = RsaUtil.generateAesKeyBytes();
                String dataStr = jsonResponse.get("data").toString();
                String encryptedData = RsaUtil.aesEncrypt(dataStr, aesKey);

                byte[] aesKeyBytes = aesKey.getBytes(StandardCharsets.UTF_8);
                String encryptedKey = Base64.getEncoder().encodeToString(
                        RsaUtil.getInstance().getRsa().encrypt(aesKeyBytes,
                                cn.hutool.crypto.asymmetric.KeyType.PublicKey)
                );

                jsonResponse.put("data", encryptedData);
                jsonResponse.put("encryptedKey", encryptedKey);
            }

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(jsonResponse.toJSONString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}