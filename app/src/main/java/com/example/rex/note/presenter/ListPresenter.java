package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.iView.IListView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

/**
 * Created by Rex on 2016/5/6.
 */
public class ListPresenter extends BasePresenter<IListView> {
    private DaoSession daoSession;
    private DiaryDao diaryDao;
    private ArrayList<Diary> diarys;

    public ListPresenter(Context context, IListView iView) {
        super(context, iView);
        daoSession = App.getDaoSession();
        diaryDao = daoSession.getDiaryDao();
    }

    @Override
    public void release() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void fetchDiaryData(int page) {
        diarys = (ArrayList<Diary>) diaryDao.queryBuilder().orderDesc(DiaryDao.Properties.Date).offset((page - 1) * 4).limit(4).list();
        Logger.i(page + "----" + diarys.size());
        if (diarys.size() == 0) {
            iView.showNoMoreData();
        } else {
            iView.showDiaryList(diarys);
        }
        iView.hideProgress();

    }
}
