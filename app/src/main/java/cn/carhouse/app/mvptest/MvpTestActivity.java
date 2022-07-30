package cn.carhouse.app.mvptest;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lven.retrofit.callback.OnCallback;

import java.util.List;

import cn.carhouse.app.R;
import cn.carhouse.app.TestActivity;
import cn.carhouse.app.mvptest.presenter.LoginPresenter;
import cn.carhouse.app.mvptest.presenter.MainPresenter;
import cn.carhouse.app.mvptest.view.ILoginView;
import cn.carhouse.app.mvptest.view.IMainView;
import cn.base.ui.mvp.MvpActivity;
import cn.base.ui.mvp.inject.InjectPresenter;
import cn.carhouse.imageloader.IImageLoader;
import cn.carhouse.imageloader.ImageLoaderFactory;
import cn.carhouse.titlebar.DefTitleBar;


public class MvpTestActivity extends MvpActivity<MainPresenter> implements IMainView, ILoginView {


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
        iv.post(new Runnable() {
            @Override
            public void run() {
                // 正常加载图片
                imageLoader.displayImage(iv, url, iv.getMeasuredWidth(), iv.getMaxHeight());
            }
        });


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
                //TSUtils.show(message);
                Toast.makeText(MvpTestActivity.this, message, Toast.LENGTH_SHORT).show();
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

    // =====LoginPresenter测试===========
    public void loginTest(View view) {
        try {
            loginPresenter.showText();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showText(String text) {
        tv.setText(text);
    }
}
