package cn.carhouse.app.mvptest.model;

import com.lven.retrofit.RetrofitPresenter;
import com.lven.retrofit.callback.IOnCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.base.ui.mvp.core.IModel;

public class MainModel implements IModel {
    public void loadList() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            items.add("item" + i);
        }
        EventBus.getDefault().post(items);
    }

    public void loadItem(IOnCallback callback) {
        RetrofitPresenter.get(null, "get", "id", "1001", callback);
    }
}
