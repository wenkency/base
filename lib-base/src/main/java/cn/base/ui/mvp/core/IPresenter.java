package cn.base.ui.mvp.core;

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
     * 获取代理的View
     */
    V getView();

    /**
     * 获取真正的View
     */
    V getRealView();

    /**
     * 获取Model
     */
    M getModel();

    /**
     * 是否注册EventBus发送数据
     *
     * @return
     */
    boolean isUseEvent();
}
