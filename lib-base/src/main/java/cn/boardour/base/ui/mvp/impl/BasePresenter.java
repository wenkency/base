package cn.boardour.base.ui.mvp.impl;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;

import cn.boardour.base.ui.mvp.core.IModel;
import cn.boardour.base.ui.mvp.core.IPresenter;
import cn.boardour.base.ui.mvp.core.IView;

/**
 * 业务处理层：负责View的控制、负责Model的处理
 *
 * @param <M> Model层
 * @param <V> View层
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter<M, V> {
    private WeakReference<V> mView;
    private V mProxyView;
    private M mModel;

    @Override
    public void attach(V view) {
        if (view == null) {
            return;
        }
        this.mView = new WeakReference<>(view);
        // 1. 使用动态代理，统一判空
        mProxyView = (V) Proxy.newProxyInstance
                (
                        IView.class.getClassLoader(),
                        ProxyUtils.getProxyClass(view, IView.class),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                // 在这里统一判空
                                if (mView == null || mView.get() == null) {
                                    return null;
                                }
                                return method.invoke(mView.get(), args);
                            }
                        }
                );
        try {
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            // 获取第一个类型参数的真实类型
            Class<M> m = (Class<M>) pt.getActualTypeArguments()[0];
            // 2. 动态创建Model
            mModel = m.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 注册EventBus
        if (isUseEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void detach() {
        if (mView != null) {
            mView.clear();
            this.mView = null;
        }
        if (mModel != null) {
            mModel = null;
        }
        if (isUseEvent()) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public V getView() {
        return mProxyView;
    }

    @Override
    public V getRealView() {
        if (mView == null || mView.get() == null) {
            return null;
        }
        return mView.get();
    }

    @Override
    public M getModel() {
        return mModel;
    }

    @Override
    public boolean isUseEvent() {
        return false;
    }

    @Subscribe
    public void onEvent(BasePresenter basePresenter) {

    }
}
