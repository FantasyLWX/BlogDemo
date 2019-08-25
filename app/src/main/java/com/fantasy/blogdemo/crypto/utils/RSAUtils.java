package com.fantasy.blogdemo.crypto.utils;

import android.util.Base64;

import com.fantasy.blogdemo.utils.ConvertUtils;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA加解密工具类
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-08-25
 *     since   : 1.0, 2019-08-25
 * </pre>
 */
public class RSAUtils {
    private static final String CHARSET = "UTF-8";

    /**
     * 加密，输出Base64字符串密文
     *
     * @param data           明文
     * @param publicKey      公钥
     * @param keySize        公钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 密文
     */
    public static String encryptBase64(String data, byte[] publicKey, int keySize, String transformation) {
        try {
            return Base64.encodeToString(handle(data.getBytes(CHARSET), publicKey, keySize, transformation,
                    true), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，输出Base64字符串密文
     *
     * @param data           明文
     * @param publicKey      公钥（Base64字符串）
     * @param keySize        公钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 密文
     */
    public static String encryptBase64(String data, String publicKey, int keySize, String transformation) {
        try {
            return Base64.encodeToString(handle(data.getBytes(CHARSET), Base64.decode(publicKey, Base64.NO_WRAP),
                    keySize, transformation, true), Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密，输出十六进制字符串密文
     *
     * @param data           明文
     * @param publicKey      公钥
     * @param keySize        公钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 密文
     */
    public static String encryptHex(String data, byte[] publicKey, int keySize, String transformation) {
        try {
            return ConvertUtils.bytesToHexString(handle(data.getBytes(CHARSET), publicKey, keySize,
                    transformation, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 加密，输出十六进制字符串密文
     *
     * @param data           明文
     * @param publicKey      公钥（Base64字符串）
     * @param keySize        公钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 密文
     */
    public static String encryptHex(String data, String publicKey, int keySize, String transformation) {
        try {
            return ConvertUtils.bytesToHexString(handle(data.getBytes(CHARSET),
                    Base64.decode(publicKey, Base64.NO_WRAP), keySize, transformation, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密，密文为Base64字符串
     *
     * @param data           密文
     * @param privateKey     私钥
     * @param keySize        私钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 明文
     */
    public static String decryptBase64(String data, byte[] privateKey, int keySize, String transformation) {
        try {
            return new String(handle(Base64.decode(data, Base64.NO_WRAP), privateKey, keySize,
                    transformation, false), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密，密文为Base64字符串
     *
     * @param data           密文
     * @param privateKey     私钥（Base64字符串）
     * @param keySize        私钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 明文
     */
    public static String decryptBase64(String data, String privateKey, int keySize, String transformation) {
        try {
            return new String(handle(Base64.decode(data, Base64.NO_WRAP), Base64.decode(privateKey, Base64.NO_WRAP),
                    keySize, transformation, false), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密，密文为十六进制字符串
     *
     * @param data           密文
     * @param privateKey     私钥
     * @param keySize        私钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 明文
     */
    public static String decryptHex(String data, byte[] privateKey, int keySize, String transformation) {
        try {
            return new String(handle(ConvertUtils.hexStringToBytes(data), privateKey, keySize, transformation, false), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密，密文为十六进制字符串
     *
     * @param data           密文
     * @param privateKey     私钥（Base64字符串）
     * @param keySize        私钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @return 明文
     */
    public static String decryptHex(String data, String privateKey, int keySize, String transformation) {
        try {
            return new String(handle(ConvertUtils.hexStringToBytes(data), Base64.decode(privateKey, Base64.NO_WRAP),
                    keySize, transformation, false), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理数据，加密或解密
     *
     * @param data           数据
     * @param key            密钥
     * @param keySize        密钥大小，举例：1024, 2048...
     * @param transformation 类型，格式为：加密算法/加密模式/填充方式，举例：<i>RSA/None/PKCS1Padding</i>。<br/>
     *                       相关取值可以查看下列两个文档：
     *                       <ul>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/api">JavaSE 8 API</a>
     *                       中的 javax.crypto.Cipher</li>
     *                       <li><a href="https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Cipher">
     *                       Standard Algorithm Name Documentation</a></li>
     *                       </ul>
     * @param isEncrypt      如果是加密，则为true；如果为解密，则为false
     * @return 加密后或解密后的字节数组
     * @throws Exception 异常
     */
    private static byte[] handle(byte[] data, byte[] key, int keySize, String transformation,
                                 boolean isEncrypt) throws Exception {
        Key rsaKey;
        if (isEncrypt) {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
        } else {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
        }
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
        int len = data.length;
        int maxLen = keySize / 8;
        if (isEncrypt) {
            String lowerTrans = transformation.toLowerCase();
            if (lowerTrans.endsWith("pkcs1padding")) {
                maxLen -= 11;
            }
        }
        int count = len / maxLen;
        if (count > 0) {
            byte[] ret = new byte[0];
            byte[] buff = new byte[maxLen];
            int index = 0;
            for (int i = 0; i < count; i++) {
                System.arraycopy(data, index, buff, 0, maxLen);
                ret = joins(ret, cipher.doFinal(buff));
                index += maxLen;
            }
            if (index != len) {
                int restLen = len - index;
                buff = new byte[restLen];
                System.arraycopy(data, index, buff, 0, restLen);
                ret = joins(ret, cipher.doFinal(buff));
            }
            return ret;
        } else {
            return cipher.doFinal(data);
        }
    }

    /**
     * 合并两个字节数组
     *
     * @param prefix 前一个字节数组
     * @param suffix 后一个字节数组
     * @return 字节数组
     */
    private static byte[] joins(byte[] prefix, byte[] suffix) {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }

}
