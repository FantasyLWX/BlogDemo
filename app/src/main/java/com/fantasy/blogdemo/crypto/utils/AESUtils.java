package com.fantasy.blogdemo.crypto.utils;

import android.util.Base64;

import com.fantasy.blogdemo.utils.ConvertUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具类
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-07-12
 *     since   : 1.0, 2019-07-12
 * </pre>
 */
public class AESUtils {
    private static final String CHARSET = "UTF-8";

    /**
     * 加密，输出Base64字符串密文
     *
     * @param data           明文
     * @param key            密钥
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：AES/CBC/PKCS5Padding，
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @param iv             偏移量，ECB模式不需要，传null
     * @return 密文
     * @throws Exception 异常
     */
    public static String encryptBase64(String data, String key, String transformation, String iv) throws Exception {
        return Base64.encodeToString(handle(data.getBytes(CHARSET), key, transformation, iv, true), Base64.NO_WRAP);
    }

    /**
     * 解密，密文为Base64字符串
     *
     * @param data           密文
     * @param key            密钥
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：AES/CBC/PKCS5Padding，
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @param iv             偏移量，ECB模式不需要，传null
     * @return 明文
     * @throws Exception 异常
     */
    public static String decryptBase64(String data, String key, String transformation, String iv) throws Exception {
        return new String(handle(Base64.decode(data, Base64.NO_WRAP), key, transformation, iv, false), CHARSET);
    }

    /**
     * 加密，输出十六进制字符串密文
     *
     * @param data           明文
     * @param key            密钥
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：AES/CBC/PKCS5Padding，
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @param iv             偏移量，ECB模式不需要，传null
     * @return 密文
     * @throws Exception 异常
     */
    public static String encryptHex(String data, String key, String transformation, String iv) throws Exception {
        return ConvertUtils.bytesToHexString(handle(data.getBytes(CHARSET), key, transformation, iv, true));
    }

    /**
     * 解密，密文为十六进制字符串
     *
     * @param data           密文
     * @param key            密钥
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：AES/CBC/PKCS5Padding，
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @param iv             偏移量，ECB模式不需要，传null
     * @return 明文
     * @throws Exception 异常
     */
    public static String decryptHex(String data, String key, String transformation, String iv) throws Exception {
        return new String(handle(ConvertUtils.hexStringToBytes(data), key, transformation, iv, false), CHARSET);
    }

    /**
     * 处理数据，加密或解密
     *
     * @param data           数据
     * @param key            密钥
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>AES/CBC/PKCS5Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @param iv             偏移量，ECB模式不需要，传null
     * @param isEncrypt      如果是加密，则为true；如果为解密，则为false
     * @return 加密后或解密后的字节数组
     * @throws Exception 异常
     */
    private static byte[] handle(byte[] data, String key, String transformation, String iv,
                                 boolean isEncrypt) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(CHARSET), "AES"); // 构造密钥
        Cipher cipher = Cipher.getInstance(transformation);
        if (iv == null || iv.length() == 0) {
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
        } else {
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes(CHARSET));
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, ips);
        }
        return cipher.doFinal(data);
    }

}