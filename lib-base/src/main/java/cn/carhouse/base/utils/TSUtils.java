package cn.carhouse.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * 打印Toast工具类
 *
 * @author Administrator
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

    @SuppressLint("ShowToast")
    private static Toast getToast(Context context, CharSequence message, int duration) {
        if (message != null && message.length() > 150) {
            message = message.subSequence(0, 50) + "...";
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, message + "", duration);
        } else {
            mToast.setText(message + "");
            mToast.setDuration(duration);
        }
        return mToast;
    }

    public static void show(final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                getToast(UIUtils.getmContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showTop(final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                getToast(UIUtils.getmContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void show(final String text, final int duration) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                getToast(UIUtils.getmContext(), text, duration).show();
            }
        });
    }
}
