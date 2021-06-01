package cn.boardour.base.ui.core;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Fragment Activity通用方法抽取
 */
public interface IBaseView {
    /**
     * 1. 初始化数据
     */
    void initData(Bundle savedInstanceState);

    /**
     * 2. 设置ContentView
     */
    void setContentView(View view);

    View getContentView();

    int getContentLayout();

    /**
     * 初始化View之前
     */
    void beforeInitViews();

    /**
     * 3.初始化标题
     */
    void initTitle();

    /**
     * 4. 初始化View
     */
    void initViews(View contentView);

    /**
     * 5. After初始化View
     */
    void afterInitViews();

    /**
     * 6. 访问网络
     */
    void initNet();


    /**
     * 要不要注册EventBus
     */
    boolean isNeedEvent();


    Activity getAppActivity();

}
