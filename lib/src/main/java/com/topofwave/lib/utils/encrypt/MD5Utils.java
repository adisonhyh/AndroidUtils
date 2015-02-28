package com.topofwave.lib.utils.encrypt;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static java.util.Locale.US;

/**
 * @describe MD5生成帮助类.
 * @author adison
 * @date: 2014-10-22 下午3:40:34 <br/>
 */
public class MD5Utils {

    /**
     * 生成hash的长度
     */
    private static final int HASH_LENGTH = 32;

    /**
     * 编码方式
     */
    private static final String CHARSET = "UTF-8"; //"CP1252";

    private static final MessageDigest MD5;

    static {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch(NoSuchAlgorithmException e) {
            digest = null;
        }
        MD5 = digest;
    }

    private static String digest(final String value) {
        if(MD5 == null)
            return null;

        byte[] bytes;
        try {
            bytes = value.getBytes(CHARSET);
        } catch(UnsupportedEncodingException e) {
            return null;
        }

        synchronized(MD5) {
            MD5.reset();
            bytes = MD5.digest(bytes);
        }

        String hashed = new BigInteger(1, bytes).toString(16);
        int padding = HASH_LENGTH - hashed.length();
        if(padding == 0)
            return hashed;

        char[] zeros = new char[padding];
        Arrays.fill(zeros, '0');
        return new StringBuilder(HASH_LENGTH).append(zeros).append(hashed).toString().toLowerCase(US);
    }

    /**
     * 根据具体字符串获取MD5值
     * @param str
     * @return hash
     */
    public static String getMD5(String str) {
        if(TextUtils.isEmpty(str))
            return null;
        str = str.trim();
        return str.length() > 0 ? digest(str) : null;
    }
}