package com.topofwave.lib.utils.file;

import java.io.File;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.topofwave.lib.utils.debug.DebugLog;

/**
 * 本应用数据清除管理器
 * <P>
 * 该类主要功能是清除data/data/packageName下的相应文件或者/mnt/sdcard/android/data/packageName/ cache
 * @author adison
 * @date 2014年4月21日
 */
public class AppDataCleanManager {

    private final static String TAG = AppDataCleanManager.class.getSimpleName();

    /**
     * 清除本应用内部缓存(/data/data/packageName/cache)
     * @param context
     */
    public static boolean cleanInternalCache(Context context) {
        return FileUtils.deleteDirectory(context.getCacheDir());

    }

    /**
     * 清除本应用所有数据库(/data/data/packageName/databases)
     * @param context
     */
    public static boolean cleanDatabases(Context context) {
        return FileUtils.deleteDirectory(new File(context.getFilesDir().getPath() + context.getPackageName() + "/databases"));
    }

    /**
     * 清除本应用SharedPreference(/data/data/packageName/shared_prefs)
     * @param context
     */
    public static boolean cleanSharedPreference(Context context) {
        return FileUtils.deleteDirectory(new File(context.getFilesDir().getPath() + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 按名字清除本应用数据库
     * @param context
     * @param dbName
     */
    public static boolean cleanDatabaseByName(Context context, String dbName) {
        return context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/packageName/files下的内容
     * @param context
     */
    public static boolean cleanFiles(Context context) {
        return FileUtils.deleteDirectory(context.getFilesDir());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/packageName/cache)
     * @param context
     */
    public static boolean cleanExternalCache(Context context) {
        if(FileUtils.isSDCardWritable()) {
            return FileUtils.deleteDirectory(context.getExternalCacheDir());
        }
        return false;
    }

    /**
     * 清除本应用所有数据
     * @param context
     */
    public static boolean cleanAPPlicationData(Context context) {
        boolean delete_1 = cleanInternalCache(context);
        boolean delete_2 = cleanExternalCache(context);
        boolean delete_3 = cleanDatabases(context);
        boolean delete_4 = cleanSharedPreference(context);
        boolean delete_5 = cleanFiles(context);
        DebugLog.i("delete_1--" + delete_1 + "--delete_2--" + delete_2 + "--delete_3--" + delete_3 + "--delete_4--" + delete_4
            + "--delete_5--" + delete_5);
        return delete_1 && delete_2 && delete_3 && delete_4 && delete_5;
    }

    /**
     * 删除网页cookie
     * @param context
     */
    public static boolean cleanCookie(Context context) {
        try {
            CookieSyncManager.createInstance(context);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            return true;
        } catch(Exception e) {
            DebugLog.i(e.getMessage());
            return false;

        }
        // String webview_cookie = context.getFilesDir().getPath() + context.getPackageName() +
        // "/databases/webviewCookiesChromium.db";
        // // String webview_cookie_private = "/data/data/" + context.getPackageName()
        // // + "/databases/webviewCookiesChromiumPrivate.db";
        // File file = new File(webview_cookie);
        // boolean result = false;
        // if(file.exists()) {
        //
        // result = FileUtils.deleteFile(file);
        // } else {
        // result = true;
        // }
        // // File file_private = new File(webview_cookie_private);
        // // FileUtil.deleteFile(file_private);
        // return result;
    }

    /**
     * 清除网页缓存
     * @param context
     * @deprecated TODO 不完全准确
     */
    public static boolean cleanWebCache(Context context) {
        String webview_db = context.getFilesDir().getPath() + context.getPackageName() + "/databases/webview.db";
        String app_db = context.getCacheDir() + File.separator + "ApplicationCache.db";
        File file = new File(webview_db);
        File app_file = new File(app_db);
        // File appCacheDir=null;
        // if(appCacheDir == null) {
        // appCacheDir=context.getCacheDir();
        // }
        boolean delete_1 = false;
        if(file.exists()) {
            delete_1 = FileUtils.deleteFile(file);
        } else {
            delete_1 = true;
        }
        boolean delete_2 = false;
        if(app_file.exists()) {
            delete_2 = FileUtils.deleteFile(app_file);
        } else {
            delete_2 = true;
        }
        return delete_1 && delete_2;
    }

}
