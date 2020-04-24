package cn.carhouse.base.ui.mvp.core;

/**
 * 业务处理层：负责View的控制、负责Model的处理
 *
 * @param <M> Model层
 * @param <V> View层
 */
public interface IPresenter<M extends IModel, V extends IView> {
    /**
     * 绑定View
     *
     * @param view
     */
    void attach(V view);

    /**
     * 解绑View
     */
    void detach();

    /**
     * 获取View
     */
    V getView();

    /**
     * 获取Model
     */
    M getModel();
}
