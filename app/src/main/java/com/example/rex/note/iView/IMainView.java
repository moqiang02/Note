package com.example.rex.note.iView;

/**
 * Created by Rex on 2016/5/3.
 */
public interface IMainView extends IBaseView {
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
}
