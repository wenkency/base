package cn.boardour.base.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mq on 2018/3/6 上午11:40
 * mqcoder90@gmail.com
 */

public class PermissionUtil {

    private static final SimpleArrayMap<String, Integer> MIN_SDK_PERMISSIONS;

    static {
        MIN_SDK_PERMISSIONS = new SimpleArrayMap<>(8);
        MIN_SDK_PERMISSIONS.put("com.android.voicemail.permission.ADD_VOICEMAIL", 14);
        MIN_SDK_PERMISSIONS.put("android.permission.BODY_SENSORS", 20);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.READ_EXTERNAL_STORAGE", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.USE_SIP", 9);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_CALL_LOG", 16);
        MIN_SDK_PERMISSIONS.put("android.permission.SYSTEM_ALERT_WINDOW", 23);
        MIN_SDK_PERMISSIONS.put("android.permission.WRITE_SETTINGS", 23);
    }

    /**
     * 判断是否所有权限都同意了，都同意返回true 否则返回false
     *
     * @param context     context
     * @param permissions permission list
     * @return return true if all permissions granted else false
     */
    public static boolean hasSelfPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(context, permission)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否所有权限都同意了，都同意返回null 否则返回拒绝权限信息描述
     */
    public static String getRefusePermission(Context context, String... permissions) {
        for (String permission : permissions) {
            if (permissionExists(permission) && !hasSelfPermission(context, permission)) {
                return Permission.getMsg(permission);
            }
        }
        return null;
    }

    /**
     * 判断单个权限是否同意
     *
     * @param context    context
     * @param permission permission
     * @return return true if permission granted
     */
    private static boolean hasSelfPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 判断权限是否存在
     *
     * @param permission permission
     * @return return true if permission exists in SDK version
     */
    private static boolean permissionExists(String permission) {
        Integer minVersion = MIN_SDK_PERMISSIONS.get(permission);
        return minVersion == null || Build.VERSION.SDK_INT >= minVersion;
    }

    /**
     * 检查是否都赋予权限
     *
     * @param grantResults grantResults
     * @return 所有都同意返回true 否则返回false
     */
    public static boolean verifyPermissions(int... grantResults) {
        if (grantResults.length == 0) return false;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查所给权限List是否需要给提示
     *
     * @param activity    Activity
     * @param permissions 权限list
     * @return 如果某个权限需要提示则返回true
     */
    public static boolean shouldShowRequestPermissionRationale(Activity activity, String... permissions) {
        for (String permission : permissions) {
            // 用户拒绝是true   用户选择不再提示是：false
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取用户未授权的权限列表
     */
    public static List<String> getDeniedPermissions(Activity activity, String... permissions) {
        List<String> list = new ArrayList<>();
        if (permissions == null) {
            return list;
        }
        for (String p : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, p) != PackageManager.PERMISSION_GRANTED) {
                list.add(p);
            }
        }
        return list;
    }


    /**
     * 判断版本是不是大于6.0
     */
    public static boolean isUpAndroidM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
