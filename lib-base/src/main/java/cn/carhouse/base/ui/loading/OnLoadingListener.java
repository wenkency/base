package cn.carhouse.base.ui.loading;

import android.view.View;

/**
 * 加载监听
 */
public abstract class OnLoadingListener {
    public abstract void setRetryEvent(View retryView);

    public void setLoadingEvent(View loadingView) {
    }

    public void setEmptyEvent(View emptyView) {
    }

    public void setDataErrorEvent(View errorDataView) {
    }

    public int generateLoadingLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateDataErrorLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return LoadingManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout() {
        return null;
    }

    public View generateRetryLayout() {
        return null;
    }

    public View generateDataErrorLayout() {
        return null;
    }

    public View generateEmptyLayout() {
        return null;
    }

    public boolean isSetLoadingLayout() {
        return generateLoadingLayoutId() != LoadingManager.NO_LAYOUT_ID || generateLoadingLayout() != null;
    }

    public boolean isSetRetryLayout() {
        return generateRetryLayoutId() != LoadingManager.NO_LAYOUT_ID || generateRetryLayout() != null;
    }

    public boolean isSetDataErrorLayout() {
        return generateDataErrorLayoutId() != LoadingManager.NO_LAYOUT_ID || generateDataErrorLayout() != null;
    }

    public boolean isSetEmptyLayout() {
        return generateEmptyLayoutId() != LoadingManager.NO_LAYOUT_ID || generateEmptyLayout() != null;
    }

    public void onLoadingChanged(LoadState state, LoadingLayout loadingLayout) {

    }

}