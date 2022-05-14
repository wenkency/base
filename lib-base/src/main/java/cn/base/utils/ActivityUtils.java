package cn.base.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Activity的跳转工具类
 */

public class ActivityUtils {


    // 集合用谁 List LinkedList Stack  ?? 删除和添加比较多
    private CopyOnWriteArrayList<Activity> mActivities;

    /**
     * 在Application初始化的时候调用一下这个方法
     */
    public final static void register(Application application) {
        if (application == null) {
            return;
        }
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                // 将Activity添加到集合内
                ActivityUtils.getInstance().attach(activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityUtils.getInstance().detach(activity);
            }
        });
    }

    private ActivityUtils() {
        // 初始化一下
        getActivities();
    }


    public final static ActivityUtils getInstance() {
        return InstanceHolder.mInstance;
    }

    private static class InstanceHolder {
        private static ActivityUtils mInstance = new ActivityUtils();
    }

    public final boolean isEmpty() {
        return mActivities == null || mActivities.isEmpty();
    }

    /**
     * 添加统一管理
     *
     * @param activity
     */
    public final void attach(Activity activity) {
        getActivities().add(activity);
    }

    /**
     * 移除解绑 - 防止内存泄漏
     *
     * @param detachActivity
     */
    public final void detach(Activity detachActivity) {
        getActivities().remove(detachActivity);
    }

    /**
     * 获取所有的Activity
     */
    public final List<Activity> getActivities() {
        if (mActivities == null) {
            mActivities = new CopyOnWriteArrayList<>();
        }
        return mActivities;
    }

    /**
     * 获取当前的Activity（最前面）
     */
    public final Activity getCurrentActivity() {
        if (isEmpty()) {
            return null;
        }
        return mActivities.get(mActivities.size() - 1);
    }

    /**
     * 关闭指定类名的Activity
     */
    public final void finish(Class<? extends Activity>... activityClass) {
        for (Activity activity : getActivities()) {
            for (Class<? extends Activity> clazz : activityClass) {
                if (activity.getClass().getCanonicalName().equals(clazz.getCanonicalName())) {
                    activity.finish();
                }
            }
        }
    }

    /**
     * 关闭所有的Activity
     */
    public final void finishAll() {
        for (Activity activity : getActivities()) {
            activity.finish();
        }
    }


    // 静态方法：打开Activity操作================================================================================================

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

    /**
     * 判断某个界面是否在前台  某个界面名称
     */
    public static boolean isForeground(Context context, String className) {
        if (context != null && !TextUtils.isEmpty(className)) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
            if (list != null && list.size() > 0) {
                ComponentName componentName = list.get(0).topActivity;
                if (componentName != null && (className.equals(componentName.getClassName()) || componentName.getClassName().contains(className))) {
                    return true;
                }
            }

        }
        return false;
    }
}
