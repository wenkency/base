package cn.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.base.ui.core.FragmentPresenter;
import cn.base.ui.core.IBaseView;

/**
 * 抽离基本的Fragment
 */
public abstract class BaseFragment extends Fragment implements IBaseView {
    protected View mContentView;
    private FragmentPresenter activityPresenter;
    private Activity mActivity;
    private boolean isInit = false;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPresenter = new FragmentPresenter();
        activityPresenter.attach(this, savedInstanceState, getLayoutInflater());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mContentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (isInit) {
            return;
        }
        if (activityPresenter != null) {
            activityPresenter.onViewCreated(mContentView);
        }
        isInit = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isInit) {
            fragmentVisible(isVisibleToUser);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        // 第一创建的时候不会走
        if (isInit) {
            fragmentVisible(!hidden);
        }
    }

    /**
     * Fragment状态改变的时候调用
     */
    public void fragmentVisible(boolean isVisibleToUser) {

    }

    @Override
    public void onDestroy() {
        if (activityPresenter != null) {
            activityPresenter.detach();
            activityPresenter = null;
        }
        mContentView = null;
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
        return mActivity;
    }

    /**
     * 默认注册
     */
/*    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseFragment fragment) {

    }*/

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

    public final <V extends View> V findViewById(int viewId) {
        return mContentView.findViewById(viewId);
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
}
