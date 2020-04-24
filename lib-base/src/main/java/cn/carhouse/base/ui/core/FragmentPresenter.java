package cn.carhouse.base.ui.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.carhouse.base.ui.KeyBordUtils;

public class FragmentPresenter<V extends IBaseView> {
    private V baseView;
    private Unbinder mBind;

    public void attach(final V view, Bundle savedInstanceState, LayoutInflater inflater) {
        if (view == null) {
            return;
        }
        this.baseView = view;
        // 1. 初始化数据
        getBaseView().initData(savedInstanceState);
        // 2. 设置ContentView
        View contentView = initContentView(inflater);
        getBaseView().setContentView(contentView);

    }

    public void onViewCreated(View contentView) {
        // 3. 绑定View
        bindView(contentView);
        // 4. 初始化标题
        getBaseView().initTitle();
        // 5. 初始化View
        getBaseView().initViews(contentView);
        // 6. 初化View之后
        getBaseView().afterInitViews();
        // 7. 网络请求
        getBaseView().initNet();
    }

    private View initContentView(LayoutInflater inflater) {
        int layoutId = getBaseView().getContentLayout();
        return inflater.inflate(layoutId, null, false);
    }


    /**
     * 绑定View
     */
    private void bindView(View view) {
        if (getBaseView().isNeedEvent()) {
            EventBus.getDefault().register(baseView);
        }
        if (getBaseView().isNeedBind()) {
            mBind = ButterKnife.bind(baseView, view);
        }
    }

    protected void unbindView() {
        // 事件
        if (getBaseView().isNeedEvent()) {
            EventBus.getDefault().unregister(baseView);
        }
        if (mBind != null) {
            mBind.unbind();
            mBind = null;
        }
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyBord() {
        KeyBordUtils.closeKeyBord(getBaseView().getAppActivity());
    }

    public void detach() {
        // 7. 解绑View
        unbindView();
        closeKeyBord();
        baseView = null;
    }

    public V getBaseView() {
        return baseView;
    }
}
