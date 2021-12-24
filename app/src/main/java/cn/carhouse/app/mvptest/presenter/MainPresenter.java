package cn.carhouse.app.mvptest.presenter;

import android.app.Activity;

import com.lven.retrofit.RetrofitPresenter;
import com.lven.retrofit.callback.IOnCallback;
import com.lven.retrofit.callback.OnCallback;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import cn.carhouse.base.ui.mvp.impl.BasePresenter;
import cn.carhouse.app.mvptest.model.MainModel;
import cn.carhouse.app.mvptest.view.IMainView;

public class MainPresenter extends BasePresenter<MainModel, IMainView> implements IMainView {

    /**
     * View的控制逻辑
     */
    public void loadList() {
        getModel().loadList();
    }

    /**
     * View的控制逻辑
     */
    public void loadItem() {
        getModel().loadItem(new OnCallback() {
            @Override
            public void onSuccess(String response) {
                showItem(response);
            }
        });
    }

    /**
     * View的显示逻辑
     */
    @Override
    public void showItem(String item) {
        getView().showItem(item);
    }

    /**
     * View的显示逻辑
     * 用EventBusn发送数据
     */
    @Subscribe
    @Override
    public void showList(List<String> items) {
        getView().showList(items);
    }


    /**
     * 加载数据
     */
    public static void loadItem(Activity activity, IOnCallback callback) {
        RetrofitPresenter.get(activity, "get", "id", "1001", callback);
    }

    @Override
    public boolean isUseEvent() {
        return true;
    }
}
