package cn.boardour.base.utils;

import android.app.Application;


/**
 * 用来配置
 */
public class ConfigUtils {
    public static void init(Application application, boolean isDebug) {
        // 初始化配置
        if (ContextUtils.isMainProcess(application)) {
            // 设置Log
            LG.setDebug(isDebug);
            // 设置Activity回调监听
            ActivityUtils.register(application);
            // 初始化Context
            ContextUtils.getInstance().init(application);
        }
    }

    public static void init(Application application) {
        init(application, false);
    }
}
