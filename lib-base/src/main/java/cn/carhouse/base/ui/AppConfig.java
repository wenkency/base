package cn.carhouse.base.ui;

import android.app.Application;

import cn.carhouse.base.ui.loading.LoadingManager;
import cn.carhouse.base.utils.ConfigUtils;

/**
 * APP页面配置类，配置一些通用的东西
 * 设置一次，整个项目默认通用
 */
public class AppConfig {


    /**
     * 这个是初始化
     */
    public static void init(Application application, boolean isDebug) {
        ConfigUtils.init(application, isDebug);
    }

    /**
     * 设置标题文本颜色
     */
    public static void setTitleColor(int color) {
        AppActivity.TITLE_TEXT_COLOR = color;
    }

    /**
     * 设置标题右边文本颜色
     */
    public static void setRightTextColor(int color) {
        AppActivity.TITLE_RIGHT_TEXT_COLOR = color;
    }

    /**
     * 设置内容背景颜色
     */
    public static void setContentBackgroundColor(int color) {
        AppActivity.CONTENT_COLOR = color;
    }

    /**
     * 设置标题背景颜色
     */
    public static void setTitleBackgroundColor(int color) {
        AppActivity.TITLE_CONTENT_COLOR = color;
    }

    /**
     * 设置返回按钮图片
     */
    public static void setTitleBackImage(int resId) {
        AppActivity.IC_TITLE_BACK = resId;
    }

    /**
     * 设置返回按钮图片
     */
    public static void setTitleBackImageFilterColor(int color) {
        AppActivity.IC_TITLE_BACK_FILTER_COLOR = color;
    }

    /**
     * 设置加载中页面
     *
     * @param layoutId 布局ID
     */
    public static void setLoadingLayoutId(int layoutId) {
        LoadingManager.BASE_LOADING_LAYOUT_ID = layoutId;
    }

    /**
     * 设置重试页面
     *
     * @param layoutId 布局ID
     */
    public static void setRetryLayoutId(int layoutId) {
        LoadingManager.BASE_RETRY_LAYOUT_ID = layoutId;
    }

    /**
     * 设置数据加载失败页面
     *
     * @param layoutId 布局ID
     */
    public static void setDataErrorLayoutId(int layoutId) {
        LoadingManager.BASE_DATA_ERROR_LAYOUT_ID = layoutId;
    }

    /**
     * 设置空页面
     *
     * @param layoutId 布局ID
     */
    public static void setEmptyLayoutId(int layoutId) {
        LoadingManager.BASE_EMPTY_LAYOUT_ID = layoutId;
    }
}
