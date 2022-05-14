package cn.base.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;



/**
 * APP版本信息相关的工具类
 */
public class AppUtils {
    // 版本名
    public static String getVersionName() {
        if (getPackageInfo() != null) {
            try {
                return getPackageInfo().versionName;
            } catch (Exception e) {
            }
        }
        return "";
    }

    // 版本号
    public static int getVersionCode() {
        if (getPackageInfo() != null) {
            try {
                return getPackageInfo().versionCode;
            } catch (Exception e) {
            }
        }
        return 0;
    }

    private static PackageInfo getPackageInfo() {
        Context context = ContextUtils.getInstance().getContext();
        if (context == null) {
            return null;
        }
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), 0);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
