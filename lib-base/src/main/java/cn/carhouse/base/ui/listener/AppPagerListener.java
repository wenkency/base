package cn.carhouse.base.ui.listener;

import android.view.View;

import cn.carhouse.base.ui.loading.LoadState;
import cn.carhouse.base.ui.loading.LoadingLayout;
import cn.carhouse.base.ui.loading.OnLoadingListener;

/**
 * 页面加载回调
 */
public interface AppPagerListener {
    /**
     * 布局回调
     */
    void setViewClick(LoadState loadState, View view);

    /**
     * 空页面ID
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
    Object getLoadingParentView();

    /**
     * 页面状态发生改变
     */
    void onLoadingChanged(LoadState state, LoadingLayout loadingLayout);
}
