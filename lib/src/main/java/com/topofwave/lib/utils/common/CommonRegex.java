package com.topofwave.lib.utils.common;

import java.util.regex.Pattern;

/**
 * @describe 常用正则表达式工具类
 * @author adison
 * @date: 2014-10-22 下午3:24:30 <br/>
 */
public class CommonRegex {

    /** 常字符，包含标点 */
    public static final String COMMON_CHAR = "^[\\w \\p{P}]+$";

    /** 空行 */
    public static final String EMPTY_LINE = "^[\\s\\n]*$";

    /** 可用作人名的字符 */
    public static final String PERSION_NAME = "^[\\w .\u00B7-]+$";

    /** 中国的电话 */
    // public static final String CN_PHONE = "^((13[0-9])|(14[5-9])|(15[^4,//D])|(18[0-9]))\\d{8}$";
    public static final String CN_PHONE = "^0?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";

    /** Email: The Official Standard: RFC 2822 **/
    public static final String EMAIL =
        "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    /**
     * 执行正则表达匹配
     * @param regex 正则表达式
     * @param str 字符内容
     * @return 如果正则表达式匹配成功，返回true，否则返回false。
     */
    public static boolean matcherRegex(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(str).matches();
    }

    /**
     * 匹配有效字符（中英文数字及常用标点）
     * @param str 字符内容
     * @return 如果正则表达式匹配成功，返回true，否则返回false。
     */
    public static boolean matchCommonChar(String str) {
        return matcherRegex(COMMON_CHAR, str);
    }

    /**
     * 判断手机号码
     * @param number 手机号码
     * @return 如果正则表达式匹配成功，返回true，否则返回false。
     */
    public static boolean matchCNMobileNumber(String number) {
        return matcherRegex(CN_PHONE, number);
    }

    /**
     * 匹配空行
     * @param line 字符内容
     * @return 如果正则表达式匹配成功，返回true，否则返回false。
     */
    public static boolean matchEmptyLine(String line) {
        return null == line ? true : matcherRegex(EMPTY_LINE, line);
    }

    /**
     * 清除标点符号
     * @param content 字符内容
     * @return 如果正则表达式匹配成功，返回true，否则返回false。
     */
    public static String cleanPunctuation(String content) {
        return content.replaceAll("[\\p{P}‘’“”]", content);
    }

    /**
     * 判断是否是邮箱格式
     * @param email
     * @return
     */
    public static boolean matchEmail(String email) {
        return null == email ? false : matcherRegex(EMAIL, email);
    }

    /**
     * 判断是否为有效的人物名字，可带·间隔符号
     * @param name 字符内容
     * @return 如果正则表达式匹配成功，返回true，否则返回false。
     */
    public static boolean matchPersionName(String name) {
        return null == name ? false : matcherRegex(PERSION_NAME, name);
    }
}
