package cn.boardour.base.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;

/**
 * Handler单例
 */
public class HandlerUtils {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private HandlerUtils() {
    }

    public static HandlerUtils getInstance() {
        return InstanceHolder.mInstance;
    }

    private static class InstanceHolder {
        private static HandlerUtils mInstance = new HandlerUtils();
    }

    public Handler handler() {
        return mHandler;
    }

    /**
     * 是否运行在主线程
     *
     * @return
     */
    public boolean isRunInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 在主线程执行一个任务
     *
     * @param runnable
     */
    public void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            // 在主线程
            runnable.run();
        } else {
            Activity currentActivity = ActivityUtils.getInstance().getCurrentActivity();
            if (currentActivity != null) {
                currentActivity.runOnUiThread(runnable);
                return;
            }
            // 不在主线程
            mHandler.post(runnable);
        }
    }

    public void postDelayed(Runnable runnable, long delayMillis) {
        mHandler.postDelayed(runnable, delayMillis);
    }

    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }
}
