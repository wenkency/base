# base
1. 项目的基本库，为快速开发项目提供基础。
2. AppConfig用这个类来配置通用的配置。

### 引入

```

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}

implementation "com.github.wenkency:base:3.0.0"



<application
        ...>
       ...

         <!--清单配置-->
        <provider
            android:name="cn.carhouse.utils.provider.InitProvider"
            android:authorities="${applicationId}.BaseProvider"
            android:exported="false"
            android:multiprocess="true" />
</application>

 // Application初始化
 ARouter.init(this);
 // 测试用，实际用自己开发的页面
 AppConfig.setLoadingLayoutId(R.layout.app_pager_loading);
 AppConfig.setRetryLayoutId(R.layout.app_pager_loading);
 AppConfig.setDataErrorLayoutId(R.layout.app_pager_loading);
 AppConfig.setEmptyLayoutId(R.layout.app_pager_loading);

```

### 已依赖
```android
    //  通用标题栏
    api this.dependLibs.titlebar
    api this.dependLibs.utils
    api this.dependLibs.eventbus
```

### 使用方式

```
public class TestActivity extends AppActivity {

    String path;
    TextView tv;


    @Override
    protected Object getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("我是另一个页面");
    }

    @Override
    protected void initViews(View view) {
        ARouter.getInstance().inject(this);
        tv = findViewById(R.id.tv);
        tv.setText(path);
    }

    @Override
    protected void initNet() {
        showLoading();
    }
}

```

### 运行结果

<img src="screenshot/image.jpg" width="360px"/>
