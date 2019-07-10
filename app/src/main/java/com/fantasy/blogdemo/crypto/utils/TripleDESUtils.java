package com.fantasy.blogdemo.crypto.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 3DES加解密工具类
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-07-10
 *     since   : 1.0, 2019-07-10
 * </pre>
 */
public class TripleDESUtils {
    private static final String CHARSET = "UTF-8";

    /**
     * ECB模式加密
     *
     * @param key  密钥
     * @param data 明文
     * @return 密文
     * @throws Exception 异常
     */
    public static String encryptECB(String key, String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(CHARSET), "DESede"); // 恢复密钥
        // transformation的相关取值可以查看下列两个文档
        // https://docs.oracle.com/javase/8/docs/api/
        // https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding"); // Cipher完成加密或解密工作类
        cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 对Cipher初始化，加密模式
        byte[] bytes = cipher.doFinal(data.getBytes(CHARSET)); // 加密data
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * ECB模式解密
     *
     * @param key  密钥
     * @param data 密文
     * @return 明文
     * @throws Exception 异常
     */
    public static String decryptECB(String key, String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(CHARSET), "DESede");
        Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey); // 对Cipher初始化，解密模式
        byte[] bytes = cipher.doFinal(Base64.decode(data, Base64.DEFAULT)); // 解密data
        return new String(bytes, CHARSET);
    }

    /**
     * CBC模式加密
     *
     * @param key  密钥
     * @param iv   偏移量
     * @param data 明文
     * @return 密文
     */
    public static String encryptCBC(String key, String iv, String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(CHARSET), "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes(CHARSET));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ips);
        byte[] bytes = cipher.doFinal(data.getBytes(CHARSET));
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * CBC模式解密
     *
     * @param key  密钥
     * @param iv   偏移量
     * @param data 密文
     * @return 明文
     */
    public static String decryptCBC(String key, String iv, String data) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(CHARSET), "DESede");
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes(CHARSET));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ips);
        byte[] bytes = cipher.doFinal(Base64.decode(data, Base64.DEFAULT));
        return new String(bytes, CHARSET);
    }

}