package com.topofwave.lib.utils.string;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @describe String工具类.
 * @author adison
 * @date: 2014-10-15 上午12:40:04 <br/>
 */
public class StringUtils {

    private static char[] chars;

    static {
        initDefaultChars();
    }

    /**
     * initDefaultChars:为{@link #generateRandom(int)}准备a-z的字符. <br/>
     * @author adison
     */
    private static void initDefaultChars() {
        String charsStr = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int length = charsStr.length();
        chars = new char[length];
        charsStr.getChars(0, length, chars, 0);
    }

    /**
     * generateRandom:生成包含随机字符的字符串. <br/>
     * @author adison
     * @param length
     * @return 随机字符串
     */
    public static String generateRandom(int length) {
        String generatedStr = null;
        if(length < 0) {
            return generatedStr;
        }

        if(length == 0) {
            return "";
        }

        char[] generatedChar = new char[length];
        int charsLength = chars.length;

        Random random = new SecureRandom();
        for(int i = 0; i < length; ++ i) {
            int index = random.nextInt(charsLength);
            generatedChar[i] = (char)chars[index];
        }

        generatedStr = String.valueOf(generatedChar);
        return generatedStr;
    }

}
