package com.topofwave.lib.utils.net;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @describe WebView工具类.
 * @author adison
 * @date: 2014-11-11 下午6:12:49 <br/>
 */
public class WebViewUtils {

    /**
     * 检查字符串是否是一个url
     */
    public static boolean isUrl(String url) {
        return url.contains(".");
    }

    /**
     * 检查一个url，如果非http://开头，增加之
     * @param url
     * @return
     */
    public static String checkUrl(String url) {
        if((url != null) && (url.length() > 0)) {

            if(( ! url.startsWith("http://")) && ( ! url.startsWith("https://")) && ( ! url.startsWith("file://"))) {

                url = "http://" + url;

            }
        }

        return url;
    }

    /**
     * 获取cookies
     * @param context
     * @param url
     * @return
     */
    public static Map<String, String> synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        String cookie = cookieManager.getCookie(url);
        Map<String, String> cookieMap = new HashMap<String, String>();
        // cookieManager.setCookie(url, "uid=1243432");
        // 添加非空判断
        if( ! TextUtils.isEmpty(cookie)) {
            String[] cookies = cookie.split(";");
            // 数组非空判断
            if(cookies != null && cookies.length > 0) {
                for(int i = 0; i < cookies.length; i ++ ) {
                    // 修改获得cookies，拆分字符串方法
                    if(cookies[i].contains("=")) {
                        String[] values = cookies[i].split("=", 2);
                        cookieMap.put(values[0].trim(), values[1].trim());
                    } else {
                        cookieMap.put(cookies[i].trim(), "");
                    }

                }
                CookieSyncManager.getInstance().sync();
                return cookieMap;
            }

        }
        return cookieMap;
    }

    /**
     * 设置cookies
     * @param context
     * @param url
     * @return
     */
    public static void setCookies(Context context, String url, String cookies) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(url, cookies);
        CookieSyncManager.getInstance().sync();
    }

    /**
     * 通过外部浏览器打开页面
     * @param context
     * @param urlText
     */
    public static void openBrowser(Context context, String urlText) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.intent.action.VIEW");
        Uri url = Uri.parse(urlText);
        intent.setData(url);
        context.startActivity(intent);
    }
}
