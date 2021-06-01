package cn.boardour.base.utils;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Toast打印工具类
 */
public class TSUtils {
    private TSUtils() {
    }

    private static Toast mToast = null;

    public static void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    private static Toast getToast(CharSequence message, int duration) {
        if (message != null && message.length() > 150) {
            message = message.subSequence(0, 50) + "...";
        }
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getInstance().getContext(), message, duration);
        } else {
            mToast.setText(message);
            mToast.setDuration(duration);
        }
        return mToast;
    }

    public static void showShort(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void show(String text, int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        // 在主线程去弹窗
        HandlerUtils.getInstance().runInMainThread(new Task(text, duration));
    }

    private static class Task implements Runnable {
        private String text;
        private int duration;

        public Task(String text, int duration) {
            this.text = text;
            this.duration = duration;
        }

        @Override
        public void run() {
            getToast(text, duration).show();
        }
    }
}
