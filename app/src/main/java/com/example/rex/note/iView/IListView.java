package com.example.rex.note.iView;

import com.example.rex.Diary;

import java.util.ArrayList;

/**
 * Created by Rex on 2016/5/6.
 */
public interface IListView extends IBaseView {
    void showDiaryList(ArrayList<Diary> diarys);

    void showNoMoreData();

    void hideProgress();

    void showProgress();
}
