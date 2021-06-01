package cn.boardour.base.ui.loading;

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
        return LoadingManager.BASE_LOADING_LAYOUT_ID;
    }

    public int generateDataErrorLayoutId() {
        return LoadingManager.BASE_DATA_ERROR_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return LoadingManager.BASE_RETRY_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return LoadingManager.BASE_EMPTY_LAYOUT_ID;
    }

    public View generateLoadingLayout(LoadingManager loadingManager) {
        return loadingManager.inflate(LoadingManager.NO_LAYOUT_ID);
    }

    public View generateRetryLayout(LoadingManager loadingManager) {
        return loadingManager.inflate(LoadingManager.NO_LAYOUT_ID);
    }

    public View generateDataErrorLayout(LoadingManager loadingManager) {
        return loadingManager.inflate(LoadingManager.NO_LAYOUT_ID);
    }

    public View generateEmptyLayout(LoadingManager loadingManager) {
        return loadingManager.inflate(LoadingManager.NO_LAYOUT_ID);
    }

    public boolean isSetLoadingLayout(LoadingManager loadingManager) {
        return generateLoadingLayoutId() != LoadingManager.NO_LAYOUT_ID || generateLoadingLayout(loadingManager) != null;
    }

    public boolean isSetRetryLayout(LoadingManager loadingManager) {
        return generateRetryLayoutId() != LoadingManager.NO_LAYOUT_ID || generateRetryLayout(loadingManager) != null;
    }

    public boolean isSetDataErrorLayout(LoadingManager loadingManager) {
        return generateDataErrorLayoutId() != LoadingManager.NO_LAYOUT_ID || generateDataErrorLayout(loadingManager) != null;
    }

    public boolean isSetEmptyLayout(LoadingManager loadingManager) {
        return generateEmptyLayoutId() != LoadingManager.NO_LAYOUT_ID || generateEmptyLayout(loadingManager) != null;
    }

    public void onLoadingChanged(LoadState state, View view) {

    }

}