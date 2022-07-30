package cn.base.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import cn.base.R;
import cn.base.ui.core.ActivityPresenter;
import cn.base.ui.core.IBaseView;

/**
 * 抽离基本的Activity
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView {
    protected View mContentView;
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
     * 3.1 初始化View和标题之前
     */
    @Override
    public void beforeInitViews() {

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


    /**
     * 打开Activity
     */
    public void startActivity(Class clazz, Bundle options, int requestCode) {
        Intent intent = new Intent(getAppActivity(), clazz);
        if (options != null) {
            intent.putExtras(options);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 打开Activity
     */
    public void startActivity(Class clazz, Bundle options) {
        startActivity(clazz, options, 0);
    }

    /**
     * 打开Activity
     */
    public void startActivity(Class clazz) {
        startActivity(clazz, null);
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        // 启动页面的动画
        if (isStartActivityAnim()) {
            startActivityAnim();
        }
    }

    @Override
    public void finish() {
        super.finish();
        // 关闭页面的动画
        if (isFinishActivityAnim()) {
            finishActivityAnim();
        }
    }

    // 是否开启启动页面的动画
    public boolean isStartActivityAnim() {
        return true;
    }

    // 可以复写这个页面改变启动动画
    public void startActivityAnim() {
        overridePendingTransition(R.anim.activity_new, R.anim.activity_out);
    }

    // 是否开启关闭页面的动画
    public boolean isFinishActivityAnim() {
        return true;
    }

    // 可以复写这个页面改变关闭动画
    public void finishActivityAnim() {
        overridePendingTransition(R.anim.activity_back, R.anim.activity_finish);
    }
}
