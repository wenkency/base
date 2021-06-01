package cn.boardour.base.ui;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import cn.boardour.base.ui.loading.LoadState;
import cn.boardour.base.ui.loading.LoadingManager;
import cn.boardour.base.ui.loading.OnLoadingListener;
import cn.boardour.base.ui.loading.listener.AppLoadingListener;
import cn.boardour.base.ui.loading.listener.AppPagerListener;

/**
 * ================================================================
 * 版权: 爱车小屋所有（C） 2019
 * <p>
 * 作者：刘付文 （61128910@qq.com）
 * <p>
 * 时间: 2019-11-14 17:12
 * <p>
 * 描述：
 * ================================================================
 */
public abstract class AppFragment extends BaseFragment implements AppPagerListener {
    /**
     * 加载页面
     */
    private LoadingManager mLoadingLayout;

    // 初始化加载页面==============================================================================
    @Override
    public void afterInitViews() {
        // 初始化加载页面
        if (isNeedLoading()) {
            // 初始化加载页面
            mLoadingLayout = LoadingManager.generate(getLoadingParent(), getOnLoadingListener());
            afterLoadingView();
        }
    }

    /**
     * 默认显示内容页面，
     * 可以复写这个方法，显示其它加载页面
     */
    protected void afterLoadingView() {
        showContent();
    }

    /**
     * 布局监听
     */
    @Override
    public OnLoadingListener getOnLoadingListener() {
        return new AppLoadingListener(this);
    }

    /**
     * 页面状态改变回调
     */
    @Override
    public void onLoadingChanged(LoadState state, View view) {

    }

    @Override
    public void onViewLoaded(@NonNull LoadState state, @NonNull View view) {
        if (view != null) {
            if (state == LoadState.RETRY || state == LoadState.DATA_ERROR) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRetryClick();
                    }
                });
            }
        }
    }

    /**
     * 要不要加载页面的布局
     *
     * @return 默认要
     */
    @Override
    public boolean isNeedLoading() {
        return true;
    }

    /**
     * 加载布局作用在哪个View上
     */
    @NonNull
    @Override
    public Object getLoadingParent() {
        return getContentView();
    }

    /**
     * 空布局
     */
    @Override
    public int getEmptyLayoutId() {
        return -1;
    }

    /**
     * 网络失败点击重新调用
     */
    protected void onRetryClick() {
        showLoading();
        initNet();
    }


    /**
     * 显示加载中的页面
     */
    protected void showLoading() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showLoading();
        }
    }

    /**
     * 显示内容页面
     */
    protected void showContent() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showContent();
        }
    }

    /**
     * 显示空页面
     */
    protected void showEmpty() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmpty();
        }
    }

    /**
     * 显示重试或者数据请求失败页面
     */
    protected void showNetOrDataError() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showNetOrDataError(getAppActivity());
        }
    }


    /**
     * 显示重试页面
     */
    protected void showRetry() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showRetry();
        }
    }

    public boolean isFinishing() {
        Activity appActivity = getAppActivity();
        if (appActivity != null) {
            return appActivity.isFinishing();
        }
        return false;
    }
    // 初始化加载页面==============================================================================
}
