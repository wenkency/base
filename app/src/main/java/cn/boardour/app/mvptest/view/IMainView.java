package cn.boardour.app.mvptest.view;

import java.util.List;

import cn.boardour.base.ui.mvp.core.IView;

public interface IMainView extends IView {

    void showItem(String item);

    void showList(List<String> items);
}
