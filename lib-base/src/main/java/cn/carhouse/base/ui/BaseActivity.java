package cn.carhouse.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import cn.carhouse.base.utils.KeyBordUtils;

/**
 * ================================================================
 * 版权: 爱车小屋所有（C） 2019
 * <p>
 * 作者：刘付文 （61128910@qq.com）
 * <p>
 * 时间: 2019-11-14 17:12
 * <p>
 * 描述：Activity基类
 * ================================================================
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected View mContentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. 初始化数据
        initData(savedInstanceState);
        // 2. 设置ContentView
        mContentView = initContentView();
        // 3. 绑定View
        bindView(mContentView);
        // 4. 初始化标题
        initTitle();
        // 5. 初始化View
        initViews(mContentView);
        // 6. 初化View之后
        afterInitViews();
        // 7. 网络请求
        initNet();
    }

    /**
     * 初始化View之后
     */
    protected void afterInitViews() {

    }

    protected void initData(Bundle savedInstanceState) {

    }

    protected View initContentView() {
        Object layout = getContentLayout();
        View contentView = null;
        if (layout instanceof View) {
            contentView = (View) layout;
        } else if (layout instanceof Integer) {
            contentView = getLayoutInflater()
                    .inflate((Integer) layout, null, false);
        }
        if (contentView == null) {
            new IllegalArgumentException("getContentLayout must View or LayoutId");
        }
        // 设置布局
        setContentView(contentView);
        return contentView;
    }

    protected abstract Object getContentLayout();

    protected void initTitle() {

    }

    protected abstract void initViews(View view);


    protected void initNet() {

    }

    /**
     * 绑定View
     */
    protected void bindView(View view) {
        if (isNeedEvent()) {
            EventBus.getDefault().register(this);
        }
    }

    protected void unbindView() {
        // 事件
        if (isNeedEvent()) {
            EventBus.getDefault().unregister(this);
        }
        mContentView = null;
    }

    /**
     * 要不要绑定，默认是绑定的
     */
    protected boolean isNeedBind() {
        return true;
    }


    /**
     * 要不要注册EventBus，默认是不用EventBus
     */
    protected boolean isNeedEvent() {
        return false;
    }

    /**
     * 默认注册
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseActivity activity) {

    }

    /**
     * 获取Actviity
     */
    public Activity getAppActivity() {
        return this;
    }


    /**
     * 关闭软键盘
     */
    protected void closeKeyBord() {
        KeyBordUtils.closeKeyBord(getAppActivity());
    }

    /**
     * 关闭软键盘
     */
    protected void closeKeyBord(View view) {
        KeyBordUtils.closeKeyBord(view);
    }

    /**
     * 打开软键盘
     */
    protected void openKeyBord(final View view) {
        KeyBordUtils.openKeyBord(view);
    }


    @Override
    protected void onDestroy() {
        // 7. 解绑View
        unbindView();
        // 关闭软件盘
        closeKeyBord();
        super.onDestroy();
    }

}
