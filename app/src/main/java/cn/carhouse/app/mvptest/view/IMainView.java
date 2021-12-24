package cn.carhouse.app.mvptest.view;

import java.util.List;

import cn.carhouse.base.ui.mvp.core.IView;

public interface IMainView extends IView {

    void showItem(String item);

    void showList(List<String> items);
}
