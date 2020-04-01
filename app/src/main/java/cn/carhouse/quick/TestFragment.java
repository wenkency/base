package cn.carhouse.quick;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import cn.carhouse.base.ui.AppFragment;
import cn.carhouse.utils.LogUtils;

public class TestFragment extends AppFragment {
    @BindView(R.id.tv)
    TextView tv;

    @Override
    protected Object getContentLayout() {
        return R.layout.fragment_test;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        LogUtils.setDebug(true);
        LogUtils.e("initData");
    }

    @Override
    protected void initViews(View view) {
        LogUtils.e("initViews");
        tv.setText(tv.getText() + "initViews");
    }

    @Override
    protected void initNet() {
        LogUtils.e("initNet");
    }


    @Override
    protected void fragmentVisible(boolean isVisibleToUser) {
        LogUtils.e("fragmentVisible  " + isVisibleToUser);
    }


}
