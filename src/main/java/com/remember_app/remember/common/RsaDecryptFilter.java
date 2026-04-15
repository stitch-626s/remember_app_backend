package com.remember_app.remember.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.*;

@Slf4j
@Component
@Order(1)
public class RsaDecryptFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String contentType = httpRequest.getContentType();

        if (contentType != null && contentType.contains("application/json")
                && ("POST".equalsIgnoreCase(httpRequest.getMethod())
                || "PUT".equalsIgnoreCase(httpRequest.getMethod()))) {

            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(httpRequest);
            String body = cachedRequest.getCachedBodyString();

            if (body != null && body.contains("encryptedData")) {
                log.info("收到加密请求，原始body：{}", body);
                try {
                    String decryptedBody = decryptRequest(body);
                    log.info("解密后body: {}", decryptedBody);
                    cachedRequest = new CachedBodyHttpServletRequest(httpRequest, decryptedBody);
                } catch (Exception e) {
                    log.error("解密失败: {}", e.getMessage(), e);

                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.setContentType("application/json;charset=UTF-8");
                    httpResponse.getWriter().write("{\"code\":\"500\",\"message\":\"请求解密失败: " + e.getMessage() + "\"}");
                    return;
                }
            }

            chain.doFilter(cachedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    private String decryptRequest(String body) throws Exception {
        com.alibaba.fastjson2.JSONObject json = com.alibaba.fastjson2.JSON.parseObject(body);
        String encryptedData = json.getString("encryptedData");
        String encryptedKey = json.getString("encryptedKey");

        if (encryptedData == null || encryptedKey == null) {
            return body;
        }

        byte[] aesKeyBytes = RsaUtil.getInstance().decryptAesKey(encryptedKey);
        return RsaUtil.aesDecrypt(encryptedData, aesKeyBytes);
    }

}
