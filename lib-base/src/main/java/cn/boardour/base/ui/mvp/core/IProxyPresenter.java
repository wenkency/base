package cn.boardour.base.ui.mvp.core;

/**
 * 解决一个类多个Presenter
 */
public interface IProxyPresenter<V extends IView> {
    /**
     * 一个是和创建绑定
     */
    void bindAndCreatePresenter(V view);

    /**
     * 一个是解绑
     */
    void unbindPresenter();
}
