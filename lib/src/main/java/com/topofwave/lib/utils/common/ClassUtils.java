package com.topofwave.lib.utils.common;

import android.text.TextUtils;

/**
 * 
 * @describe Class工具类 
 * @author adison
 * @date: 2014-10-22 下午3:24:04 <br/>
 */
public class ClassUtils {

   
    /**
     * isPresent:检查一个类是否存在. <br/>
     * @author adison
     * @param clazzName 所找类的full class path
     * @return true 如果类存在
     */
    public static boolean isPresent(String clazzName) {
        if(TextUtils.isEmpty(clazzName)) {
            return false;
        }

        boolean isPresent;

        try {
            @SuppressWarnings("unused")
            Class<?> clazz = Class.forName(clazzName);
            isPresent = true;
        } catch(ClassNotFoundException e) {
            isPresent = false;
        }
        return isPresent;
    }
}
