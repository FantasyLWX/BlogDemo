package com.fantasy.blogdemo.crypto.utils;

import java.security.MessageDigest;

/**
 * 加解密帮助类
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-07-10
 *     since   : 1.0, 2019-07-10
 * </pre>
 */
public class CryptoHelper {
    private static final String CHARSET = "UTF-8";

    /**
     * MD5加密
     *
     * @param data 明文
     * @return 密文
     * @throws Exception 异常
     */
    public static String encryptMD5(String data) throws Exception {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(data.getBytes(CHARSET));
        StringBuilder builder = new StringBuilder(bytes.length * 2);
        for (byte a : bytes) { // 将字节数组转成十六进制字符串
            builder.append(hexDigits[(a >> 4) & 0x0F]);
            builder.append(hexDigits[a & 0x0F]);
        }
        return builder.toString();
    }

}
