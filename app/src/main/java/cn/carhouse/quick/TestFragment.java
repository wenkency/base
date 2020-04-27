package cn.carhouse.quick;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cn.carhouse.base.ui.AppFragment;
import cn.carhouse.utils.LogUtils;

public class TestFragment extends AppFragment {
    TextView tv;

    @Override
    public int getContentLayout() {
        return R.layout.fragment_test;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        LogUtils.setDebug(true);
        LogUtils.e("initData");
    }

    @Override
    public void initViews(View view) {
        LogUtils.e("initViews");
        tv = findViewById(R.id.tv);
        tv.setText(tv.getText() + "initViews");
    }

    @Override
    public void initNet() {
        LogUtils.e("initNet");
    }


    @Override
    public void fragmentVisible(boolean isVisibleToUser) {
        LogUtils.e("fragmentVisible  " + isVisibleToUser);
    }


}
