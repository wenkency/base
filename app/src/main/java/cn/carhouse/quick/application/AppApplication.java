package cn.carhouse.quick.application;

import android.app.Application;
import com.lven.retrofit.api.RestConfig;

import cn.carhouse.base.ui.AppConfig;
import cn.carhouse.quick.R;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 测试用，实际用自己开发的页面
        AppConfig.setLoadingLayoutId(R.layout.pager_loading);
        AppConfig.setRetryLayoutId(R.layout.pager_retry);
        AppConfig.setDataErrorLayoutId(R.layout.pager_data_error);
        AppConfig.setEmptyLayoutId(R.layout.pager_empty);
        // 设置标题颜色
        AppConfig.setTitleBackgroundColor(this.getResources().getColor(R.color.colorPrimaryDark));

        RestConfig.getInstance()
                .setBaseUrl("http://httpbin.org/")
                .setDebugUrl("http://httpbin.org/")
                .setDebug(true)
                // 可设置10内再次请求，走缓存
                .register(this);
    }
}
