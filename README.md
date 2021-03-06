# base
1. 项目的基本库，为快速开发项目提供基础。
2. AppConfig用这个类来配置通用的配置。
3. 支持MVP写法。

### 引入

```

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
// eventbus
implementation "org.greenrobot:eventbus:3.2.0"
// 图片加载
implementation "com.github.wenkency:imageloader:2.7.0"
implementation "com.github.bumptech.glide:glide:4.12.0"
annotationProcessor "com.github.bumptech.glide:compiler:4.12.0"
// 图片加载

// 这两个是Android依赖包，项目有用到。
// implementation this.dependLibs.appcompat
// implementation this.dependLibs.constraintlayout

// Application初始化
// 测试用，实际用自己开发的页面
AppConfig.setLoadingLayoutId(R.layout.app_pager_loading);
AppConfig.setRetryLayoutId(R.layout.app_pager_loading);
AppConfig.setDataErrorLayoutId(R.layout.app_pager_loading);
AppConfig.setEmptyLayoutId(R.layout.app_pager_loading);

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

### 使用参考

// 弹窗使用参考
https://github.com/wenkency/quickalert.git
// RecyclerView adapter使用参考
https://github.com/wenkency/quickadapter.git
// 图片加载使用参考
https://github.com/wenkency/imageloader.git
