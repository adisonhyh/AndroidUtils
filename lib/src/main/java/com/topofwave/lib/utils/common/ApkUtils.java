package com.topofwave.lib.utils.common;

import com.topofwave.lib.utils.debug.DebugLog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

/**
 * @describe APK相关功能通用帮助类.
 * @author adison
 * @date: 2014-10-22 下午3:15:26 <br/>
 */
public class ApkUtils {

    /**
     * 安装一个APK文件
     * @param file
     * @param context
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 安装一个APK文件
     * @param context
     * @param data
     * @param type
     */
    public static void installApk(Context context, Uri data, String type) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(data, type);
        context.startActivity(intent);
    }

    /**
     * 打开某个应用
     * @param context
     * @param packageName
     */
    public static void startApp(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        // 如果该程序不可启动（像系统自带的包，有很多是没有入口的）会返回NULL
        if(intent != null)
            context.startActivity(intent);
    }

    /**
     * 判断APK包是否已经安装
     * @param context 上下文，一般为Activity
     * @param packageName 包名
     * @return 包存在则返回true，否则返回false
     */
    public static boolean isPackageExists(Context context, String packageName) {
        if(null == packageName || "".equals(packageName)) {
            throw new IllegalArgumentException("Package name cannot be null or empty !");
        }
        try {
            ApplicationInfo info =
                context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return null != info;
        } catch(NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取APK包信息
     * @param context 上下文，一般为Activity
     * @param packageName 包名
     * @return 包存在则返回true，否则返回false
     */
    public static PackageInfo getPackageInfo(Context context, String packageName) {
        if(null == packageName || "".equals(packageName)) {
            throw new IllegalArgumentException("Package name cannot be null or empty !");
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return info;
        } catch(NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 修改apk文件权限
     * @param permission
     * @param path
     */
    public static void chmod(String permission, String path) {
        try {
            String command = "chmod " + permission + " " + path;
            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
        } catch(IOException e) {
            DebugLog.i(e.getMessage());
        }

    }


}
