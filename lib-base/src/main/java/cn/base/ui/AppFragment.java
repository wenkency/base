package cn.base.ui;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.lven.loading.LoadState;
import com.lven.loading.LoadingManager;
import com.lven.loading.OnLoadingListener;
import com.lven.loading.listener.AppLoadingListener;
import com.lven.loading.listener.AppPagerListener;


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
    public void onRetryClick() {
        showLoading(isShowContent());
        initNet();
    }

    /**
     * 重试是否显示内容页面
     */
    public boolean isShowContent() {
        return false;
    }

    /**
     * 显示加载中的页面
     */
    public void showLoading() {
        showLoading(false);
    }

    /**
     * 显示加载中的页面
     */
    public void showLoading(boolean isShowContent) {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showLoading(isShowContent);
        }
    }

    /**
     * 显示内容页面
     */
    public void showContent() {
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
    public void showEmpty() {
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
    public void showNetOrDataError() {
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
    public void showRetry() {
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
