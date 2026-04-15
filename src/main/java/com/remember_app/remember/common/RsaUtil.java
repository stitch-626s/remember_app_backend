package com.remember_app.remember.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Slf4j
@Component
public class RsaUtil {

    @Value("${remember.security.rsa-private-key}")
    private String privateKeyBase64;

    @Getter
    private RSA rsa;

    private static final int AES_KEY_SIZE = 32;
    public static final int CBC_IV_SIZE = 16;

    @Getter
    private static RsaUtil instance;

    @PostConstruct
    public void init() {
        rsa = new RSA(privateKeyBase64, null);
        instance = this;
    }

    public byte[] decryptAesKey(String encryptedAesKeyBase64) {
        log.info("开始解密AES密钥, 长度: {}", encryptedAesKeyBase64.length());
        try {
            byte[] base64Bytes = rsa.decrypt(encryptedAesKeyBase64, KeyType.PrivateKey);
            String base64Key = new String(base64Bytes, StandardCharsets.UTF_8);

            log.info("AES密钥解密成功");
            return Base64.decode(base64Key);
        } catch (Exception e) {
            log.error("AES密钥解密失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    public static String generateAesKeyBytes(){
        byte[] key = new byte[AES_KEY_SIZE];
        new SecureRandom().nextBytes(key);
        return new String(key, StandardCharsets.UTF_8);
    }

    public static String aesEncrypt(String data, String aesKey) throws Exception {
        byte[] iv = new byte[CBC_IV_SIZE];
        new SecureRandom().nextBytes(iv);

        SecretKeySpec keySpec = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.encode(combined);
    }

    public static String aesDecrypt(String encryptedData, byte[] aesKey) throws Exception {
        log.info("开始AES解密, 密钥长度: {}, 数据长度: {}", aesKey != null ? aesKey.length : "null", encryptedData.length());

        try {
            byte[] combined = Base64.decode(encryptedData);
            byte[] iv = new byte[CBC_IV_SIZE];
            byte[] encrypted = new byte[combined.length - CBC_IV_SIZE];
            System.arraycopy(combined, 0, iv, 0, CBC_IV_SIZE);
            System.arraycopy(combined, CBC_IV_SIZE, encrypted, 0, encrypted.length);

            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            log.info("AES解密成功");
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES解密失败: {}", e.getMessage(), e);
            throw e;
        }

    }

}
