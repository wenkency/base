package cn.carhouse.base.ui;

import android.graphics.Color;
import android.view.View;

import cn.carhouse.base.R;
import cn.carhouse.base.ui.listener.AppLoadingListener;
import cn.carhouse.base.ui.listener.AppPagerListener;
import cn.carhouse.base.ui.loading.LoadState;
import cn.carhouse.base.ui.loading.LoadingLayout;
import cn.carhouse.base.ui.loading.LoadingManager;
import cn.carhouse.base.ui.loading.OnLoadingListener;
import cn.carhouse.titlebar.DefTitleBar;
import cn.carhouse.titlebar.DefTitleBuilder;

/**
 * ================================================================
 * 版权: 爱车小屋所有（C） 2019
 * <p>
 * 作者：刘付文 （61128910@qq.com）
 * <p>
 * 时间: 2019-11-14 17:12
 * <p>
 * 描述：
 * ================================================================
 */
public abstract class AppActivity extends BaseActivity implements AppPagerListener {
    /**
     * 返回按钮，用户可以在Application更改
     */
    public static int IC_TITLE_BACK = R.drawable.ic_title_back;

    /**
     * 返回按钮图片过滤颜色
     */
    public static int IC_TITLE_BACK_FILTER_COLOR = Color.WHITE;
    /**
     * 内容背景颜色
     */
    public static int CONTENT_COLOR = Color.WHITE;
    /**
     * 标题颜色
     */
    public static int TITLE_TEXT_COLOR = Color.WHITE;
    /**
     * 标题栏的颜色
     */
    public static int TITLE_CONTENT_COLOR = Color.parseColor("#008577");

    protected DefTitleBar mTitleBar;

    /**
     * 加载页面
     */
    private LoadingManager mLoadingLayout;


    // 初始化标题栏==============================================================================
    @Override
    public void initTitle() {
        if (isNeedTitle()) {
            mTitleBar = new DefTitleBuilder(this)
                    // 返回按钮
                    .setBackImageRes(IC_TITLE_BACK)
                    .setBackImageFilterColor(IC_TITLE_BACK_FILTER_COLOR)
                    .build();
            // 主题风格
            mTitleBar.colorStyle(TITLE_CONTENT_COLOR, CONTENT_COLOR);
            // 标题字体颜色
            mTitleBar.setTitleColor(TITLE_TEXT_COLOR);
            // 初始化设置Title
            initTitle(mTitleBar);
        }
    }

    @Override
    public void initViews(View view) {

    }

    /**
     * 初始化设置Title
     */
    protected abstract void initTitle(DefTitleBar titleBar);

    /**
     * 要不要标题，默认是要的
     */
    protected boolean isNeedTitle() {
        return true;
    }
    // 初始化标题栏==============================================================================

    // 初始化加载页面==============================================================================
    @Override
    public void afterInitViews() {
        // 初始化加载页面
        if (isNeedLoading()) {
            // 初始化加载页面
            mLoadingLayout = LoadingManager.generate(getLoadingParentView(), getOnLoadingListener());
            afterLoadingView();
        }
    }

    /**
     * 默认显示内容页面，
     * 可以复写这个方法，显示其它加载页面
     */
    protected void afterLoadingView() {
        showContent();
    }

    /**
     * 布局监听
     */
    @Override
    public OnLoadingListener getOnLoadingListener() {
        return new AppLoadingListener(this);
    }

    /**
     * 页面状态改变回调
     *
     * @param state         状态
     * @param loadingLayout 加载View
     */
    @Override
    public void onLoadingChanged(LoadState state, LoadingLayout loadingLayout) {

    }

    /**
     * 要不要加载页面的布局
     *
     * @return 默认要
     */
    @Override
    public boolean isNeedLoading() {
        return true;
    }

    /**
     * 加载布局作用在哪个View上
     */
    @Override
    public Object getLoadingParentView() {
        return getContentView();
    }


    /**
     * 设置错误View的点击
     *
     * @param view
     */
    @Override
    public void setViewClick(LoadState loadState, View view) {
        if (view != null) {
            if (loadState == LoadState.RETRY || loadState == LoadState.DATA_ERROR) {
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onRetryClick();
                    }
                });
            }
        }
    }

    /**
     * 空布局
     */
    @Override
    public int getEmptyLayoutId() {
        return -1;
    }

    /**
     * 网络失败点击重新调用
     */
    protected void onRetryClick() {
        showLoading();
        initNet();
    }


    /**
     * 显示加载中的页面
     */
    protected void showLoading() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showLoading();
        }
    }

    /**
     * 显示内容页面
     */
    protected void showContent() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showContent();
        }
    }

    /**
     * 显示空页面
     */
    protected void showEmpty() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showEmpty();
        }
    }

    /**
     * 显示重试或者数据请求失败页面
     */
    protected void showNetOrDataError() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.setNetOrData(getApplication());
        }
    }

    /**
     * 显示重试页面
     */
    protected void showRetry() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showRetry();
        }
    }
    // 初始化加载页面==============================================================================
}
