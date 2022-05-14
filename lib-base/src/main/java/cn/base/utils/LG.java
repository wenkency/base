package cn.base.utils;

import android.util.Log;


/**
 * Log打印的工具类
 */
public class LG {
    public static final String TAG = LG.class.getSimpleName();
    private static boolean isDebug = false;

    /**
     * 设置要不要打印，默认是关闭的
     */
    public static void setDebug(boolean isDebug) {
        LG.isDebug = isDebug;
    }


    public static void i(String tag, String msg) {
        if (isDebug) {
            Log.i(tag, msg + "");
        }
    }

    public static void i(String msg) {
        i(TAG, msg);
    }


    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg + "");
        }
    }

    public static void e(String msg) {
        e(TAG, msg);
    }
}
