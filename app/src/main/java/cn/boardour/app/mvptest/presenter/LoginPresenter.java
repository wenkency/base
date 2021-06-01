package cn.boardour.app.mvptest.presenter;

import cn.boardour.base.ui.mvp.impl.BasePresenter;
import cn.boardour.app.mvptest.model.LoginModel;
import cn.boardour.app.mvptest.view.ILoginView;

/**
 * 另外一个Presenter
 */
public class LoginPresenter extends BasePresenter<LoginModel, ILoginView> {
    public void showText() {
        getView().showText(getModel().getText());
    }
}
