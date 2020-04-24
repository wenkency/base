package cn.carhouse.base.ui.mvp;

import android.os.Bundle;

import androidx.annotation.Nullable;

import cn.carhouse.base.ui.AppActivity;
import cn.carhouse.base.ui.mvp.core.IPresenter;
import cn.carhouse.base.ui.mvp.core.IProxyPresenter;
import cn.carhouse.base.ui.mvp.core.IView;
import cn.carhouse.base.ui.mvp.impl.ProxyPresenter;


public abstract class MvpActivity<P extends IPresenter> extends AppActivity implements IView {
    protected P mPresenter;
    private IProxyPresenter mProxyPresenter;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        bindPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * 绑定Presenter
     */
    private final void bindPresenter() {
        // 1. 绑定常用的
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attach(this);
        }
        // 2. 创建和绑定其它
        mProxyPresenter = createProxyPresenter();
        if (mProxyPresenter != null) {
            mProxyPresenter.bindAndCreatePresenter(this);
        }
        // 3. 在这里
    }

    protected IProxyPresenter createProxyPresenter() {
        if (mProxyPresenter == null) {
            mProxyPresenter = new ProxyPresenter();
        }
        return mProxyPresenter;
    }

    /**
     * 解绑Presenter
     */
    private final void unbindPresenter() {
        if (mPresenter != null) {
            mPresenter.detach();
        }
        if (mProxyPresenter != null) {
            mProxyPresenter.unbindPresenter();
        }
    }

    @Override
    protected void onDestroy() {
        unbindPresenter();
        super.onDestroy();
    }


    protected abstract P createPresenter();


}
