package cn.carhouse.app.mvptest.presenter;

import cn.carhouse.app.mvptest.model.LoginModel;
import cn.carhouse.app.mvptest.view.ILoginView;
import cn.carhouse.base.ui.mvp.impl.BasePresenter;

/**
 * 另外一个Presenter
 */
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {
    public void showText() {
        getView().showText(getModel().getText());
    }

}
