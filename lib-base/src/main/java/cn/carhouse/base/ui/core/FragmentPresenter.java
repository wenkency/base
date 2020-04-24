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
        baseView.initData(savedInstanceState);
        // 2. 设置ContentView
        View contentView = initContentView(inflater);
        baseView.setContentView(contentView);

    }

    public void onViewCreated(View contentView) {
        // 3. 绑定View
        bindView(contentView);
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
     * 绑定View
     */
    private void bindView(View view) {
        if (baseView.isNeedEvent()) {
            EventBus.getDefault().register(baseView);
        }
        if (baseView.isNeedBind()) {
            mBind = ButterKnife.bind(baseView, view);
        }
    }

    protected void unbindView() {
        // 事件
        if (baseView.isNeedEvent()) {
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
        KeyBordUtils.closeKeyBord(baseView.getAppActivity());
    }

    public void detach() {
        // 7. 解绑View
        unbindView();
        closeKeyBord();
        baseView = null;
    }

}
