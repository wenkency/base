package cn.carhouse.quick.application;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.carhouse.base.ui.AppConfig;
import cn.carhouse.quick.R;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        // 测试用，实际用自己开发的页面
        AppConfig.setLoadingLayoutId(R.layout.app_pager_loading);
        AppConfig.setRetryLayoutId(R.layout.app_pager_loading);
        AppConfig.setDataErrorLayoutId(R.layout.app_pager_loading);
        AppConfig.setEmptyLayoutId(R.layout.app_pager_loading);
    }
}
