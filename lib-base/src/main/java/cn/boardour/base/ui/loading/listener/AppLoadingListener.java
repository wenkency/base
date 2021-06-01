package cn.boardour.base.ui.loading.listener;

import android.view.View;

import androidx.annotation.NonNull;

import cn.boardour.base.ui.loading.LoadState;
import cn.boardour.base.ui.loading.OnLoadingListener;


/**
 * 页面加载布局回调监听
 */
public class AppLoadingListener extends OnLoadingListener {
    private AppPagerListener listener;

    public AppLoadingListener(@NonNull AppPagerListener listener) {
        this.listener = listener;
    }

    @Override
    public void setRetryEvent(@NonNull View retryView) {
        if (listener != null) {
            listener.onViewLoaded(LoadState.RETRY, retryView);
        }
    }

    @Override
    public void setDataErrorEvent(@NonNull View errorDataView) {
        if (listener != null) {
            listener.onViewLoaded(LoadState.DATA_ERROR, errorDataView);
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
    public void setEmptyEvent(@NonNull View emptyView) {
        if (listener != null) {
            listener.onViewLoaded(LoadState.EMPTY, emptyView);
        }
    }

    @Override
    public void onLoadingChanged(@NonNull LoadState state, @NonNull View view) {
        if (listener != null) {
            listener.onLoadingChanged(state, view);
        }
    }
}
