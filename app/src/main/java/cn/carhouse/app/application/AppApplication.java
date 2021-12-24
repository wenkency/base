package cn.carhouse.app.application;

import android.app.Application;

import com.lven.retrofit.api.RestConfig;

import cn.carhouse.app.BuildConfig;
import cn.carhouse.app.R;
import cn.carhouse.base.ui.AppConfig;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 加载过程相关页面，测试用，实际用自己开发的页面
        AppConfig.init(this, BuildConfig.DEBUG);
        AppConfig.setLoadingLayoutId(R.layout.pager_loading);
        AppConfig.setRetryLayoutId(R.layout.pager_retry);
        AppConfig.setDataErrorLayoutId(R.layout.pager_data_error);
        AppConfig.setEmptyLayoutId(R.layout.pager_empty);
        // 设置标题颜色
        AppConfig.setTitleBackgroundColor(this.getResources().getColor(R.color.colorAccent));
        AppConfig.setTitleBackImage(R.drawable.ic_title_back);
        // 网络测试
        RestConfig.getInstance()
                .setBaseUrl("http://httpbin.org/")
                .setDebugUrl("http://httpbin.org/")
                .setDebug(true)
                // 可设置10内再次请求，走缓存
                .register(this);
    }
}
