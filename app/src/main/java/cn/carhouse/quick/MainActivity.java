package cn.carhouse.quick;

import android.view.View;

import cn.carhouse.base.ui.AppActivity;
import cn.carhouse.base.ui.FragmentUtils;
import cn.carhouse.titlebar.DefTitleBar;


public class MainActivity extends AppActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initTitle(DefTitleBar titleBar) {
        titleBar.setTitle("我是主页面");
        // 不要返回按钮
        titleBar.clearBackImage();
    }

    @Override
    public void initViews(View view) {
        FragmentUtils.changeFragment(getSupportFragmentManager(), R.id.fl_fragment, new TestFragment());
    }


    @Override
    public boolean isNeedEvent() {
        return true;
    }

}
