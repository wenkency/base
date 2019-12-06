package cn.carhouse.base.ui.loading;

/**
 * 加载状态
 */
public enum LoadState {
    // 加载中、重试、加载后台数据错误、空、显示内容
    LOADING, RETRY, DATA_ERROR, EMPTY, CONTENT,IDLE
}
