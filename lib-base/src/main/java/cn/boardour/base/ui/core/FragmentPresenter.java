package cn.boardour.base.ui.core;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import org.greenrobot.eventbus.EventBus;

import cn.boardour.base.ui.KeyBordUtils;

public class FragmentPresenter<V extends IBaseView> {
    private V baseView;

    public void attach(final V view, Bundle savedInstanceState, LayoutInflater inflater) {
        if (view == null) {
            return;
        }
        this.baseView = view;
        registerEvent();
        // 1. 初始化数据
        baseView.initData(savedInstanceState);
        // 2. 设置ContentView
        View contentView = initContentView(inflater);
        baseView.setContentView(contentView);

    }

    public void onViewCreated(View contentView) {
        // 3. 初始化View之前
        baseView.beforeInitViews();
        // 4. 初始化标题
        baseView.initTitle();
        // 5. 初始化View
        baseView.initViews(contentView);
        // 6. 初化View之后
        baseView.afterInitViews();
        // 7. 网络请求
        baseView.initNet();
    }

    private View initContentView(LayoutInflater inflater) {
        int layoutId = baseView.getContentLayout();
        return inflater.inflate(layoutId, null, false);
    }


    /**
     * 注册EventBus
     */
    private void registerEvent() {
        if (baseView.isNeedEvent()) {
            EventBus.getDefault().register(baseView);
        }
    }

    /**
     * 解绑EventBus
     */
    private void unregisterEvent() {
        // 事件
        if (baseView.isNeedEvent()) {
            EventBus.getDefault().unregister(baseView);
        }
    }

    /**
     * 关闭软键盘
     */
    private void closeKeyBord() {
        if (baseView != null) {
            KeyBordUtils.closeKeyBord(baseView.getAppActivity());
            // 优化键盘内存漏泄
            KeyBordUtils.fixInputMethodManagerLeak(baseView.getAppActivity());
        }
    }

    public void detach() {
        unregisterEvent();
        // 7. 解绑View
        if (baseView != null && baseView.isNeedEvent()) {
            unregisterEvent();
        }
        closeKeyBord();
        baseView = null;
    }

}
