package cn.carhouse.quick.mvptest.presenter;

import cn.carhouse.base.ui.mvp.impl.BasePresenter;
import cn.carhouse.quick.mvptest.model.LoginModel;
import cn.carhouse.quick.mvptest.view.ILoginView;

/**
 * 另外一个Presenter
 */
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {
    public void showText() {
        getView().showText(getModel().getText());
    }
}
