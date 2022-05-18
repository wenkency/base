package cn.base.ui;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;

import com.lven.loading.LoadState;
import com.lven.loading.LoadingManager;
import com.lven.loading.OnLoadingListener;
import com.lven.loading.listener.AppLoadingListener;
import com.lven.loading.listener.AppPagerListener;

import cn.base.R;
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
     * 标题文本颜色
     */
    public static int TITLE_TEXT_COLOR = Color.WHITE;
    /**
     * 标题右边文本颜色
     */
    public static int TITLE_RIGHT_TEXT_COLOR = Color.WHITE;
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
                    .setBackImageRes(titleBackImageRes())
                    .setBackImageFilterColor(titleBackImageFilterColor())
                    .build();
            // 主题风格
            mTitleBar.colorStyle(titleColor(), contentColor());
            // 标题字体颜色
            mTitleBar.setTitleColor(titleTextColor());
            // 右边文字的颜色
            mTitleBar.setRightTextColor(titleRightTextColor());
            // 初始化设置Title
            initTitle(mTitleBar);
        }
    }

    // ---------标题相关设置---------
    protected int titleBackImageRes() {
        return IC_TITLE_BACK;
    }

    protected int titleBackImageFilterColor() {
        return IC_TITLE_BACK_FILTER_COLOR;
    }

    protected int titleColor() {
        return TITLE_CONTENT_COLOR;
    }

    protected int contentColor() {
        return CONTENT_COLOR;
    }

    protected int titleTextColor() {
        return TITLE_TEXT_COLOR;
    }

    protected int titleRightTextColor() {
        return TITLE_TEXT_COLOR;
    }
    // ---------标题相关设置---------

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
            mLoadingLayout = LoadingManager.generate(getLoadingParent(), getOnLoadingListener());
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
     * View的状态发生改变
     */
    @Override
    public void onLoadingChanged(LoadState state, View view) {

    }

    /**
     * View加载完成
     */
    @Override
    public void onViewLoaded(@NonNull LoadState state, @NonNull View view) {
        if (view != null) {
            if (state == LoadState.RETRY || state == LoadState.DATA_ERROR) {
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
    @NonNull
    @Override
    public Object getLoadingParent() {
        return getContentView();
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
    public void onRetryClick() {
        showLoading(isShowContent());
        initNet();
    }

    /**
     * 重试是否显示内容页面
     */
    public boolean isShowContent() {
        return false;
    }

    /**
     * 显示加载中的页面
     */
    public void showLoading() {
        showLoading(false);
    }

    /**
     * 显示加载中的页面
     */
    public void showLoading(boolean isShowContent) {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showLoading(isShowContent);
        }
    }

    /**
     * 显示内容页面
     */
    public void showContent() {
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
    public void showEmpty() {
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
    public void showNetOrDataError() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showNetOrDataError(getApplication());
        }
    }

    /**
     * 显示重试页面
     */
    public void showRetry() {
        if (isFinishing()) {
            return;
        }
        if (mLoadingLayout != null) {
            mLoadingLayout.showRetry();
        }
    }
    // 初始化加载页面==============================================================================
}
