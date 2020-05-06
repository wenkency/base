package cn.carhouse.quick;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

import cn.carhouse.base.ui.AppActivity;
import cn.carhouse.base.ui.FragmentUtils;
import cn.carhouse.titlebar.DefTitleBar;


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

    @Override
    public void initNet() {

    }
}
