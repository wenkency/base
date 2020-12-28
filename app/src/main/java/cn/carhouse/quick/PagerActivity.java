package cn.carhouse.quick;

import android.content.Intent;
import android.view.View;

import cn.carhouse.base.ui.AppActivity;
import cn.carhouse.titlebar.DefTitleBar;

public class PagerActivity extends AppActivity {
    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("加载页面测试");
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_loading;
    }

    @Override
    public Object getLoadingParentView() {
        return findViewById(R.id.fl_load_pager);
    }

    public void test(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }

    public void loading(View view) {
        showLoading();
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
    }
}
