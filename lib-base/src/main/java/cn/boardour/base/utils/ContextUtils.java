package cn.boardour.base.utils;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import java.util.List;

/**
 * Context帮助类
 */
public class ContextUtils {
    private Application mApplication;

    public static ContextUtils getInstance() {
        return InstanceHolder.mInstance;
    }

    private static class InstanceHolder {
        private static ContextUtils mInstance = new ContextUtils();
    }
    public void init(Application application) {
        mApplication = application;
    }

    public Application application() {
        if (mApplication == null) {
            mApplication = ApplicationUtils.getApplication();
        }
        return mApplication;
    }

    public final Context getContext() {
        return application().getApplicationContext();
    }


    /**
     * 是不是主进程
     */
    public static boolean isMainProcess(Application application) {
        ActivityManager am = ((ActivityManager) application.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = application.getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
