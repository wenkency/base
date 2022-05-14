package cn.base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 */
public class SPUtils {
    private static final String CONFIG = "CONFIG";

    private static Context getContext() {
        return ContextUtils.getInstance().getContext();
    }

    //CT 建议使用　
    public static SharedPreferences getSharedPreferences() {
        if (getContext() == null) {
            return null;
        }
        return getContext().getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
    }

    public static void clear() {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return;
        }
        sp.edit().clear().commit();
    }

    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return;
        }
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return defValue;
        }
        return sp.getBoolean(key, defValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static void putString(String key, String value) {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return;
        }
        sp.edit().putString(key, value).commit();
    }


    public static String getString(String key, String defValue) {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return "";
        }
        return sp.getString(key, defValue);
    }

    public static String getString(String key) {
        return getString(key, "");
    }

    public static void putInt(String key, int value) {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return;
        }
        sp.edit().putInt(key, value).commit();
    }


    public static int getInt(String key, int defValue) {
        SharedPreferences sp = getSharedPreferences();
        if (sp == null) {
            return defValue;
        }
        return sp.getInt(key, defValue);
    }


}
