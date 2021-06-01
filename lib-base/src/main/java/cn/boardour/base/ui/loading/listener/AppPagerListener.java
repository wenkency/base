package cn.boardour.base.ui.loading.listener;

import android.view.View;

import androidx.annotation.NonNull;

import cn.boardour.base.ui.loading.LoadState;
import cn.boardour.base.ui.loading.OnLoadingListener;


/**
 * 页面加载回调，给基类用
 */
public interface AppPagerListener {
    /**
     * 1. 布局加载完成回调，在这里可以设置点击事件
     */
    void onViewLoaded(@NonNull LoadState state, @NonNull View view);

    /**
     * 定制空页面，空页面ID
     */
    int getEmptyLayoutId();


    /**
     * 布局监听
     */
    OnLoadingListener getOnLoadingListener();

    /**
     * 要不要加载页面的布局
     */
    boolean isNeedLoading();

    /**
     * 加载布局作用在哪个View上
     */
    @NonNull
    Object getLoadingParent();

    /**
     * 页面状态发生改变
     */
    void onLoadingChanged(@NonNull LoadState state, @NonNull View view);
}
