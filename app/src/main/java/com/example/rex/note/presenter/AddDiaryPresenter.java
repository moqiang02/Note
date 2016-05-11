package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.iView.IAddDiaryView;
import com.example.rex.note.model.entity.RxEvent;
import com.example.rex.note.util.RxBus;

import java.util.List;


/**
 * Created by Rex on 2016/5/7.
 */
public class AddDiaryPresenter extends BasePresenter<IAddDiaryView> {
    private DaoSession daoSession;
    private DiaryDao diaryDao;

    public AddDiaryPresenter(Context context, IAddDiaryView iView) {
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

    public void saveDiary(Diary diary) {
        List<Diary> diaries = diaryDao.queryBuilder().where(DiaryDao.Properties.Date.eq(diary.getDate())).build().list();
        if (diaries.size() <= 0) {
            diaryDao.insert(diary);
        } else {
            Diary diary1 = diaries.get(0);
            diary1.setContent(diary.getContent());
            diaryDao.insertOrReplace(diary1);
        }
        RxBus.getDefault().post(new RxEvent.AddDiary(true));
        iView.finishActivity();

    }
}
