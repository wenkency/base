package cn.carhouse.base.ui.listener;

import android.view.View;

import cn.carhouse.base.ui.loading.LoadState;
import cn.carhouse.base.ui.loading.LoadingLayout;
import cn.carhouse.base.ui.loading.OnLoadingListener;

/**
 * 页面加载布局回调监听
 */
public class AppLoadingListener extends OnLoadingListener {
    private AppPagerListener listener;

    public AppLoadingListener(AppPagerListener listener) {
        this.listener = listener;
    }

    @Override
    public void setRetryEvent(View retryView) {
        if (listener != null) {
            listener.setViewClick(LoadState.RETRY, retryView);
        }
    }

    @Override
    public void setDataErrorEvent(View errorDataView) {
        if (listener != null) {
            listener.setViewClick(LoadState.DATA_ERROR, errorDataView);
        }
    }

    @Override
    public int generateEmptyLayoutId() {
        int emptyLayoutId = -1;
        if (listener != null) {
            emptyLayoutId = listener.getEmptyLayoutId();
        }
        if (emptyLayoutId != -1) {
            return emptyLayoutId;
        }
        return super.generateEmptyLayoutId();
    }

    @Override
    public void setEmptyEvent(View emptyView) {
        if (listener != null) {
            listener.setViewClick(LoadState.EMPTY, emptyView);
        }
    }

    @Override
    public void onLoadingChanged(LoadState state, LoadingLayout loadingLayout) {
        if (listener != null) {
            listener.onLoadingChanged(state, loadingLayout);
        }
    }
}
