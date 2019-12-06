package cn.carhouse.base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity的跳转工具类
 */

public class ActivityUtils {
    /**
     * 打开Activity
     */
    public static final void startActivity(Context context, Class<?> clazz) {
        startActivity(context, clazz, null);
    }

    /**
     * 打开Activity
     */
    public static final void startActivity(Context context, Class<?> clazz, Bundle bundle) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        }
        context.startActivity(intent);
    }
}
