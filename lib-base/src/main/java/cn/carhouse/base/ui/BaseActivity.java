package cn.carhouse.base.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.carhouse.base.ui.core.ActivityPresenter;
import cn.carhouse.base.ui.core.IBaseView;

/**
 * 抽离基本的Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    private View mContentView;
    private ActivityPresenter<IBaseView> activityPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPresenter = new ActivityPresenter<>();
        activityPresenter.attach(this, savedInstanceState, getLayoutInflater());
    }

    @Override
    protected void onDestroy() {
        if (activityPresenter != null) {
            activityPresenter.detach();
            activityPresenter = null;
        }
        mContentView = null;
        // 移除当前Activity下的所有的Fragment
        FragmentUtils.removeAllFragments(getSupportFragmentManager());
        super.onDestroy();
    }

    /**
     * 1. 初始化数据
     */
    @Override
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * 2. 设置ContentView
     */
    @Override
    public final void setContentView(View view) {
        super.setContentView(view);
        mContentView = view;
    }

    @Override
    public View getContentView() {
        return mContentView;
    }

    @Override
    public abstract int getContentLayout();

    /**
     * 3.1 绑定View
     */
    @Override
    public void bindView(View contentView) {

    }

    @Override
    public void unbindView() {

    }

    /**
     * 3.2 初始化标题
     */
    @Override
    public void initTitle() {

    }

    /**
     * 4. 初始化View
     */
    @Override
    public void initViews(View contentView) {

    }

    /**
     * 5. After初始化View
     */
    @Override
    public void afterInitViews() {

    }

    /**
     * 6. 访问网络
     */
    @Override
    public void initNet() {

    }

    /**
     * 默认绑定ButterKnife
     */
    @Override
    public boolean isNeedBind() {
        return true;
    }

    /**
     * 默认不注册EventBus
     */
    @Override
    public boolean isNeedEvent() {
        return false;
    }


    @Override
    public Activity getAppActivity() {
        return this;
    }


    /**
     * 默认注册
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseActivity activity) {

    }

    /**
     * 关闭软键盘
     */
    public void closeKeyBord() {
        KeyBordUtils.closeKeyBord(getAppActivity());
    }

    /**
     * 关闭软键盘
     */
    public void closeKeyBord(View view) {
        KeyBordUtils.closeKeyBord(view);
    }

    /**
     * 打开软键盘
     */
    public void openKeyBord(final View view) {
        KeyBordUtils.openKeyBord(view);
    }
}
