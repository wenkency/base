package cn.carhouse.quick;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.carhouse.base.ui.AppActivity;
import cn.carhouse.imageloader.ImageLoaderFactory;
import cn.carhouse.titlebar.DefTitleBar;


public class MainActivity extends AppActivity {


    TextView tv;
    ImageView iv;

    @Override
    protected Object getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("我是主页面");
    }

    @Override
    protected void initViews(View view) {
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
        tv.setText("我是绑定的View");

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1573796397226&di=4ebc060b0d6f6a6509cdd08737d24e3a&imgtype=0&src=http%3A%2F%2Fpic26.nipic.com%2F20121225%2F9252150_165232606338_2.jpg";
        ImageLoaderFactory.getInstance().displayRadiusImage(iv, url, 50);
        ImageLoaderFactory.getInstance().displayImage(tv, url);

    }

    public void ivClick(View view) {
        String url = "carhouse://news/b/videos?path=https://img.car-house.cn/media/product/1001/20170821/20170821100649189.mp4";
        Uri uri = Uri.parse(url);
        ARouter.getInstance()
                .build(uri)
                .navigation(this);
    }

    @Override
    protected boolean isNeedEvent() {
        return true;
    }

}
