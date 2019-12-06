package cn.carhouse.base.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.carhouse.base.utils.KeyBordUtils;

/**
 * ================================================================
 * 版权: 爱车小屋所有（C） 2019
 * <p>
 * 作者：刘付文 （61128910@qq.com）
 * <p>
 * 时间: 2019-11-14 17:12
 * <p>
 * 描述：Fragment基类
 * ================================================================
 */
public abstract class BaseFragment extends Fragment {
    private Activity mActivity;
    protected View mContentView;
    private Unbinder mUnbinder;

    private boolean isInit;

    public Activity getAppActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initContentView();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (!isInit) {
            // 3. 绑定View
            bindView(mContentView);
            // 4. 初始化标题
            initTitle();
            // 5. 初始化View
            initViews(mContentView);
            // 6. 初化View之后
            afterInitViews();
            // 7. 初始化网络
            initNet();
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
    protected void fragmentVisible(boolean isVisibleToUser) {

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
        return contentView;
    }

    protected abstract Object getContentLayout();

    protected void initTitle() {

    }

    protected abstract void initViews(View view);

    /**
     * 初始化View之后
     */
    protected void afterInitViews() {

    }

    protected void initNet() {

    }


    protected void bindView(View view) {
        if (isNeedEvent()) {
            EventBus.getDefault().register(this);
        }
        if (isNeedBind()) {
            mUnbinder = ButterKnife.bind(this, view);
        }
    }

    protected void unbindView() {
        // 事件
        if (isNeedEvent()) {
            EventBus.getDefault().unregister(this);
        }
        // 绑定View
        if (isNeedBind() && mUnbinder != null) {
            mUnbinder.unbind();
            mUnbinder = null;
        }
        mContentView = null;
    }

    /**
     * 要不要绑定
     */
    protected boolean isNeedBind() {
        return true;
    }


    /**
     * 要不要注册EventBus
     */
    protected boolean isNeedEvent() {
        return false;
    }

    /**
     * 默认注册
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseFragment fragment) {

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
    protected void openKeyBord(View view) {
        KeyBordUtils.openKeyBord(view);
    }


    @Override
    public void onDestroy() {
        // 7. 解绑View
        unbindView();
        // 关闭软件盘
        closeKeyBord();
        super.onDestroy();
    }

}
