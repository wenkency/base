package cn.base.ui.mvp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.ParameterizedType;

import cn.base.ui.AppActivity;
import cn.base.ui.mvp.core.IPresenter;
import cn.base.ui.mvp.core.IView;
import cn.base.ui.mvp.impl.ProxyPresenter;
import cn.base.ui.mvp.core.IProxyPresenter;


public abstract class MvpActivity<P extends IPresenter> extends AppActivity implements IView {
    protected P mPresenter;
    private IProxyPresenter mProxyPresenter;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        bindPresenter();
        super.onCreate(savedInstanceState);
        onCreateMvp(savedInstanceState);

    }

    protected void onCreateMvp(Bundle savedInstanceState) {

    }

    /**
     * 绑定Presenter
     */
    private final void bindPresenter() {
        // 1. 绑定常用的
        Log.e("TAG","bindPresenter");
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

    /**
     * 创建Presenter
     * 子类要指定泛型类型如：MainActivity extends MvpActivity<MainPresenter>
     */
    protected P createPresenter() {
        try {
            ParameterizedType superClass = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class actualType = (Class) superClass.getActualTypeArguments()[0];
            mPresenter = (P) actualType.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mPresenter;
    }

    public P getPresenter() {
        return mPresenter;
    }
}
