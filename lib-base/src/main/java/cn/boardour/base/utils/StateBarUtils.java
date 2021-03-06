package cn.boardour.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * 1. 获取状态栏高度
 */
public class StateBarUtils {
    /**
     * 获得状态栏的高度
     */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 设置6.0状态栏字体颜色
     *
     * @param activity
     * @param isDark   true 黑色 false 白色
     */
    public static void setMStateBarFontColor(Activity activity, boolean isDark) {
        if (activity == null || activity.getWindow() == null || activity.getWindow().getDecorView() == null) {
            return;
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isDark) {
                activity.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                // 默认的
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_VISIBLE);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置状态栏的颜色
     *
     * @param activity
     * @param color
     * @param isResource 是不是资源
     */
    public static void setStatusTranslucent(final Activity activity, final int color, final boolean isResource) {
        //4.4以下
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        setStatusBar(activity);
        // 设置为透明的状态栏
        // 5.0以上
        final Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (isResource) {
                // android系统级的资源id得这么拿,不然拿不到
                activity.getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        int identifier = activity.getResources().getIdentifier("statusBarBackground",
                                "id", "android");
                        View statusBarView = window.findViewById(identifier);
                        if (statusBarView != null) {
                            statusBarView.setBackgroundResource(color);
                        }
                    }
                });
            } else {
                window.setStatusBarColor(color);
            }
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_VISIBLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4- 5.0之间的
            // 创建一个View
            View view = new View(activity);
            if (isResource) {
                view.setBackgroundResource(color);
            } else {
                view.setBackgroundColor(color);
            }
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusHeight(activity)));
            // 获取到decorView
            ViewGroup decorView = (ViewGroup) window.getDecorView();
            decorView.addView(view);
        }
    }

    /**
     * 设置状态栏颜色
     */
    public static void setStatusTranslucent(Activity activity, int color) {
        setStatusTranslucent(activity, color, false);
    }

    /**
     * 设置状态栏为透明
     */
    public static void setStatusTranslucent(Activity activity) {
        setStatusTranslucent(activity, Color.TRANSPARENT);
    }


    /**
     * 浸入式状态栏实现同时取消5.0以上的阴影
     */
    public static void setStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        Window window = activity.getWindow();
        // 设置虚拟键盘跟着屏幕自动
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }
}
