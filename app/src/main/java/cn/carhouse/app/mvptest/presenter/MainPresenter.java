package cn.carhouse.app.mvptest.presenter;

import android.app.Activity;

import com.lven.retrofit.RetrofitPresenter;
import com.lven.retrofit.callback.IOnCallback;
import com.lven.retrofit.callback.OnCallback;


import java.util.List;

import cn.base.ui.mvp.impl.BasePresenter;
import cn.carhouse.app.mvptest.model.MainModel;
import cn.carhouse.app.mvptest.view.IMainView;

public class MainPresenter extends BasePresenter<MainModel, IMainView> {

    /**
     * View的控制逻辑
     */
    public void loadList() {
        getModel().loadList(getView());
    }

    /**
     * View的控制逻辑
     */
    public void loadItem() {
        getModel().loadItem(new OnCallback() {
            @Override
            public void onSuccess(String response) {
                getView().showItem(response);
            }
        });
    }

    /**
     * 加载数据
     */
    public static void loadItem(Activity activity, IOnCallback callback) {
        RetrofitPresenter.get(activity, "get", "id", "1001", callback);
    }
}
