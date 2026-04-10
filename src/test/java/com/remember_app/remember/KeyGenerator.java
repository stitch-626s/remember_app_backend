package com.remember_app.remember;

import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.RSA;

import java.security.KeyPair;

public class KeyGenerator {
    public static void main(String[] args) {
        // 1. 创建 RSA 对象，默认生成 2048 位密钥（当前工业标准，兼顾安全与性能）
        KeyPair keyPair = KeyUtil.generateKeyPair("RSA", 2048);
        RSA rsa = new RSA(keyPair.getPrivate(), keyPair.getPublic());

        // 2. 获取私钥（给后端：Base64 编码字符串）
        String privateKey = rsa.getPrivateKeyBase64();
        // 3. 获取公钥（给前端：Base64 编码字符串）
        String publicKey = rsa.getPublicKeyBase64();

        System.out.println("----- 后端私钥 (Private Key) -----");
        System.out.println(privateKey);
        System.out.println("\n----- 前端公钥 (Public Key) -----");
        System.out.println(publicKey);
    }
}