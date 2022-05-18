package cn.carhouse.app.mvptest.model;

import com.lven.retrofit.RetrofitPresenter;
import com.lven.retrofit.callback.IOnCallback;


import java.util.ArrayList;
import java.util.List;

import cn.base.ui.mvp.core.IModel;
import cn.carhouse.app.mvptest.view.IMainView;

public class MainModel implements IModel {
    /**
     * 加载列表数据
     */
    public void loadList(IMainView mainView) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("item" + i);
        }
        mainView.showList(items);
    }

    /**
     * 网络加载数据
     */
    public void loadItem(IOnCallback callback) {
        RetrofitPresenter.get(null, "get", "id", "1001", callback);
    }

}
