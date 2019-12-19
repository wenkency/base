package cn.carhouse.base.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

/**
 * UI工具类
 * <p>
 * 在Application初始化一下
 * <p>
 */
public class UIUtils {
    // 上下文
    private static Context mContext;
    // Handler消息管理对象
    private static Handler mHandler = new Handler(Looper.getMainLooper());

    public static void init(Context context) {
        mContext = context;
    }

    /**
     * 上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext.getApplicationContext();
    }


    /**
     * Handler消息管理对象
     *
     * @return
     */
    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 是否运行在主线程
     *
     * @return
     */
    public static boolean isRunInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    /**
     * 在主线程执行一个任务
     *
     * @param runnable
     */
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            // 在主线程
            runnable.run();
        } else {
            // 不在主线程
            getHandler().post(runnable);
        }
    }

    /**
     * 取得资源对象
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 根据资源ID获取到字符串
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }

    /**
     * 根据一组资源取得字符串数组
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);

    }

    /**
     * 颜色选择器
     *
     * @return
     */
    public static ColorStateList getColorStateList(int id) {
        return getResources().getColorStateList(id);
    }

    public static int getColor(int id) {
        return getResources().getColor(id);
    }

    public static Drawable getDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 设置控件   setDrawableTop
     *
     * @param tv
     * @param icon
     */
    public static void setDrawableTop(TextView tv, int icon) {
        Drawable nav_up = getResources().getDrawable(icon);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(),
                nav_up.getMinimumHeight());
        tv.setCompoundDrawables(null, nav_up, null, null);
    }

    /**
     * 设置控件   setDrawableLeft
     *
     * @param tv
     * @param icon
     */
    public static void setDrawableLeft(TextView tv, int icon) {
        Drawable nav_up = getResources().getDrawable(icon);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(),
                nav_up.getMinimumHeight());
        tv.setCompoundDrawables(nav_up, null, null, null);
    }

    /**
     * 设置控件   DrawableRight
     *
     * @param tv
     * @param icon
     */
    public static void setDrawableRight(TextView tv, int icon) {
        Drawable nav_up = getResources().getDrawable(icon);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        tv.setCompoundDrawables(null, null, nav_up, null);
    }

    public static void setDrawableNull(TextView tv) {
        tv.setCompoundDrawables(null, null, null, null);
    }

    /**
     * 下划线
     */
    public static void setUnderlineText(TextView tv) {
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    /**
     * 删除线
     */
    public static void setStrikeText(TextView tv) {
        tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public static void setEditTextPass(EditText et, boolean isShow) {

        if (isShow) {
            et.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            et.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }


    public static String getString(int id, Object... formatArgs) {
        return String.format(getResources().getString(id), formatArgs);
    }

    public static int getIdentifier(String name, String defType) {
        int resId = getResources().getIdentifier(name, defType, getContext().getPackageName());
        return resId;
    }


}
