package cn.carhouse.app;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import cn.base.ui.AppActivity;
import cn.carhouse.alert.QuickBuilder;
import cn.carhouse.alert.QuickDialog;
import cn.carhouse.app.mvptest.MvpTestActivity;
import cn.carhouse.titlebar.DefTitleBar;


public class PagerActivity extends AppActivity {
    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("加载页面测试");
        // 不要返回按钮
        titleBar.clearBackImage();
        titleBar.setRightText("MvpTest", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MvpTestActivity.class);
            }
        });
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_loading;
    }

    @NonNull
    @Override
    public Object getLoadingParent() {
        return findViewById(R.id.fl_load_pager);
    }


    public void test(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void loading(View view) {
        showLoading(true);
    }

    @Override
    public boolean isShowContent() {
        return true;
    }

    public void retry(View view) {
        showRetry();
    }

    public void dataError(View view) {
        showNetOrDataError();
    }

    public void empty(View view) {
        showEmpty();
    }

    public void content(View view) {
        showContent();

        final QuickDialog popup = new QuickBuilder(this)
                .setContentView(R.layout.dialog_window)
                .setWidthScale(0.8f)
                .build();
        popup.show();
    }

    @Override
    public void initNet() {
        mContentView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showContent();
            }
        }, 300);
    }
}
