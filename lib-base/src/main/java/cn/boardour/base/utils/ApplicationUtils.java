package cn.boardour.base.utils;

import android.app.Application;

import java.lang.reflect.Method;

class ApplicationUtils {
    public final static Application getApplication() {
        try {
            Class<?> clz = Class.forName("android.app.ActivityThread");
            Method method = clz.getDeclaredMethod("currentApplication", null);
            method.setAccessible(true);
            Application application = (Application) method.invoke(null, null);
            return application;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        throw new NullPointerException("getApplication is failed");
    }
}
