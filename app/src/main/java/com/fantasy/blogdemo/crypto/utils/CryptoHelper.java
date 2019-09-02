package com.fantasy.blogdemo.crypto.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密帮助类，包括信息摘要算法、安全散列算法、散列信息认证码算法
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-09-02
 *     since   : 1.0, 2019-07-10
 * </pre>
 */
public class CryptoHelper {
    /**
     * MD2加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptMD2ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * MD2加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptMD2ToString(byte[] data) {
        return bytesToHexString(encryptMD2(data));
    }

    /**
     * MD2加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptMD2(byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * MD5加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptMD5ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param salt 盐
     * @return 十六进制字符串密文
     */
    public static String encryptMD5ToString(String data, String salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return bytesToHexString(encryptMD5(data.getBytes()));
        if (data == null) return bytesToHexString(encryptMD5(salt.getBytes()));
        return bytesToHexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptMD5ToString(byte[] data) {
        return bytesToHexString(encryptMD5(data));
    }

    /**
     * MD5加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param salt 盐
     * @return 十六进制字符串密文
     */
    public static String encryptMD5ToString(byte[] data, byte[] salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return bytesToHexString(encryptMD5(data));
        if (data == null) return bytesToHexString(encryptMD5(salt));
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return bytesToHexString(encryptMD5(dataSalt));
    }

    /**
     * MD5加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptMD5(byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * 使用MD5对文件进行加密
     *
     * @param filePath 文件路径
     * @return 文件密文
     */
    public static String encryptMD5FileToString(String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5FileToString(file);
    }

    /**
     * 使用MD5对文件进行加密
     *
     * @param filePath 文件路径
     * @return 文件密文
     */
    public static byte[] encryptMD5File(String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * 使用MD5对文件进行加密
     *
     * @param file 文件
     * @return 文件密文
     */
    public static String encryptMD5FileToString(File file) {
        return bytesToHexString(encryptMD5File(file));
    }

    /**
     * 使用MD5对文件进行加密
     *
     * @param file 文件
     * @return 文件密文
     */
    public static byte[] encryptMD5File(File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * SHA1加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA1ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA1ToString(byte[] data) {
        return bytesToHexString(encryptSHA1(data));
    }

    /**
     * SHA1加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptSHA1(byte[] data) {
        return hashTemplate(data, "SHA-1");
    }

    /**
     * SHA224加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA224ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA224ToString(byte[] data) {
        return bytesToHexString(encryptSHA224(data));
    }

    /**
     * SHA224加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptSHA224(byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA256加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA256ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA256ToString(byte[] data) {
        return bytesToHexString(encryptSHA256(data));
    }

    /**
     * SHA256加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptSHA256(byte[] data) {
        return hashTemplate(data, "SHA-256");
    }

    /**
     * SHA384加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA384ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA384ToString(byte[] data) {
        return bytesToHexString(encryptSHA384(data));
    }

    /**
     * SHA384加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptSHA384(byte[] data) {
        return hashTemplate(data, "SHA-384");
    }

    /**
     * SHA512加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA512ToString(String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @return 十六进制字符串密文
     */
    public static String encryptSHA512ToString(byte[] data) {
        return bytesToHexString(encryptSHA512(data));
    }

    /**
     * SHA512加密
     *
     * @param data 明文
     * @return 密文
     */
    public static byte[] encryptSHA512(byte[] data) {
        return hashTemplate(data, "SHA-512");
    }

    /**
     * 信息摘要算法和安全散列算法的模板
     *
     * @param data      明文
     * @param algorithm 算法
     * @return 密文
     */
    private static byte[] hashTemplate(byte[] data, String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * HmacMD5加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacMD5ToString(String data, String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacMD5加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacMD5ToString(byte[] data, byte[] key) {
        return bytesToHexString(encryptHmacMD5(data, key));
    }

    /**
     * HmacMD5加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] encryptHmacMD5(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * HmacSHA1加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA1ToString(String data, String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA1加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA1ToString(byte[] data, byte[] key) {
        return bytesToHexString(encryptHmacSHA1(data, key));
    }

    /**
     * HmacSHA1加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] encryptHmacSHA1(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * HmacSHA224加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA224ToString(String data, String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA224ToString(byte[] data, byte[] key) {
        return bytesToHexString(encryptHmacSHA224(data, key));
    }

    /**
     * HmacSHA224加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] encryptHmacSHA224(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * HmacSHA256加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA256ToString(String data, String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA256加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA256ToString(byte[] data, byte[] key) {
        return bytesToHexString(encryptHmacSHA256(data, key));
    }

    /**
     * HmacSHA256加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] encryptHmacSHA256(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * HmacSHA384加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA384ToString(String data, String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA384加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA384ToString(byte[] data, byte[] key) {
        return bytesToHexString(encryptHmacSHA384(data, key));
    }

    /**
     * HmacSHA384加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] encryptHmacSHA384(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * HmacSHA512加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA512ToString(String data, String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA512加密，输出十六进制字符串密文
     *
     * @param data 明文
     * @param key  密钥
     * @return 十六进制字符串密文
     */
    public static String encryptHmacSHA512ToString(byte[] data, byte[] key) {
        return bytesToHexString(encryptHmacSHA512(data, key));
    }

    /**
     * HmacSHA512加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static byte[] encryptHmacSHA512(byte[] data, byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * 信息认证码算法
     *
     * @param data      明文
     * @param key       密钥
     * @param algorithm 算法
     * @return 明文
     */
    private static byte[] hmacTemplate(byte[] data, byte[] key, String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};

    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
