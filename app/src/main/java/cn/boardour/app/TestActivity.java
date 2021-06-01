package cn.boardour.app;

import android.view.View;

import cn.boardour.base.ui.AppActivity;
import cn.boardour.base.ui.FragmentUtils;
import cn.boardour.base.ui.titlebar.DefTitleBar;


public class TestActivity extends AppActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("我是另一个页面");
    }

    @Override
    public void initViews(View view) {
        FragmentUtils.replaceFragment(getSupportFragmentManager(), R.id.fl_fragment, new TestFragment());
    }
}
