package com.topofwave.lib.utils.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 配置帮助类
 * 
 * @date 2014-3-19
 */
public class SettingUtils {

	private static SharedPreferences sharedPreferences = null;

	private static Editor editor = null;

	private SettingUtils() {

	}

	public static SharedPreferences getSharedPreferencesObject(Context context) {
		if (sharedPreferences == null)
			sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(context);
		return sharedPreferences;
	}

	private static Editor getEditorObject(Context context) {
		if (editor == null)
			editor = PreferenceManager.getDefaultSharedPreferences(context)
					.edit();
		return editor;
	}

	public static int getSharedPreferences(Context context, String paramString,
			int paramInt) {
		return getSharedPreferencesObject(context)
				.getInt(paramString, paramInt);
	}

	public static long getSharedPreferences(Context context,
			String paramString, long paramLong) {
		return getSharedPreferencesObject(context).getLong(paramString,
				paramLong);
	}

	public static Boolean getSharedPreferences(Context context,
			String paramString, Boolean paramBoolean) {
		return getSharedPreferencesObject(context).getBoolean(paramString,
				paramBoolean);
	}

	public static String getSharedPreferences(Context context,
			String paramString1, String paramString2) {
		return getSharedPreferencesObject(context).getString(paramString1,
				paramString2);
	}

	public static void setEditor(Context context, String paramString,
			int paramInt) {
		getEditorObject(context).putInt(paramString, paramInt).commit();
	}

	public static void setEditor(Context context, String paramString,
			long paramLong) {
		getEditorObject(context).putLong(paramString, paramLong).commit();
	}

	public static void setEditor(Context context, String paramString,
			Boolean paramBoolean) {
		getEditorObject(context).putBoolean(paramString, paramBoolean).commit();
	}

	public static void setEditor(Context context, String paramString1,
			String paramString2) {
		getEditorObject(context).putString(paramString1, paramString2).commit();
	}

	// Delete
	public static void remove(Context context, String key) {
		getEditorObject(context).remove(key).commit();
	}

	public static void clear(Context context) {
		getEditorObject(context).clear().commit();
	}

}
