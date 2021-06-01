package cn.boardour.app.mvptest;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lven.retrofit.callback.OnCallback;

import java.util.List;

import cn.boardour.app.R;
import cn.boardour.app.TestActivity;
import cn.boardour.app.mvptest.presenter.LoginPresenter;
import cn.boardour.app.mvptest.presenter.MainPresenter;
import cn.boardour.app.mvptest.view.IMainView;
import cn.boardour.base.ui.mvp.MvpActivity;
import cn.boardour.base.ui.mvp.inject.InjectPresenter;
import cn.boardour.base.ui.titlebar.DefTitleBar;
import cn.boardour.base.utils.TSUtils;
import cn.carhouse.imageloader.IImageLoader;
import cn.carhouse.imageloader.ImageLoaderFactory;


public class MvpTestActivity extends MvpActivity<MainPresenter> implements IMainView {


    TextView tv;
    private ImageView iv;

    @InjectPresenter
    private LoginPresenter loginPresenter;

    @Override
    public int getContentLayout() {
        return R.layout.activity_mvp_test;
    }

    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("我是主页面");
        titleBar.setRightTextColor(Color.WHITE);
        titleBar.setRightText("测试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TestActivity.class);
            }
        });
    }

    @Override
    public void initViews(View view) {
        tv = findViewById(R.id.tv);
        iv = findViewById(R.id.iv);
        String url = "https://img.car-house.cn/Upload/activity/20191126/EMBkW2wXZ8MHjXDTR8p6PjmcGTD44xdD.gif";

        IImageLoader imageLoader = ImageLoaderFactory.getInstance();
        // 正常加载图片
        imageLoader.displayImage(iv, url);

    }

    public void loadItem(View view) {
        //  getPresenter().loadItem();


        MainPresenter.loadItem(this, new OnCallback() {
            @Override
            public void onSuccess(String response) {
                showItem(response);
            }

            @Override
            public void onError(int code, String message) {
                TSUtils.show(message);
            }
        });
    }

    public void loadList(View view) {
        getPresenter().loadList();
    }

//    @Override
//    public void initViews(View view) {
//        FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.fl_fragment, new TestFragment());
//    }


    @Override
    public void showItem(String item) {
        tv.setText(item);
    }

    @Override
    public void showList(List<String> items) {
        tv.setText(items.toString());
    }


}
