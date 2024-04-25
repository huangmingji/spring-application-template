package com.stargazer.springapplicationtemplate.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordSecurity {
    
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int SALT_BYTE_SIZE = 16; // 盐的字节数
    private static final int DERIVED_KEY_LENGTH = 32; // 派生密钥长度（字节）
    private static final int PBKDF2_ITERATIONS = 64000; // 迭代次数

    public static String createSecretKey()
    {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    public static String createHash(String password, String securityKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Base64.getDecoder().decode(securityKey);
        // 创建密钥规范
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2_ITERATIONS, DERIVED_KEY_LENGTH * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);

        // 生成密钥
        byte[] encryptedBytes = skf.generateSecret(spec).getEncoded();

        // 将盐与加密后的密文合并以方便存储或验证
        byte[] combined = new byte[salt.length + encryptedBytes.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(encryptedBytes, 0, combined, salt.length, encryptedBytes.length);

        // 返回Base64编码后的结果
        return Base64.getEncoder().encodeToString(combined);
    }
}
