package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.iView.IAddDiaryView;
import com.example.rex.note.model.entity.RxEvent;
import com.example.rex.note.util.RxBus;


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
        Diary diary1 = diaryDao.queryBuilder().where(DiaryDao.Properties.Date.eq(diary.getDate())).build().unique();
        if (diary1 == null) {
            diaryDao.insert(diary);
        } else {
            diary1.setContent(diary.getContent());
            diaryDao.insertOrReplace(diary1);
        }
        RxBus.getDefault().post(new RxEvent.AddDiary(true));
        iView.finishActivity();

    }
}
