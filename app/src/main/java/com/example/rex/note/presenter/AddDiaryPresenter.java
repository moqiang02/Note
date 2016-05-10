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
        if (subscription !=null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    public void saveDiary(String date,String content) {
        String[] dateArr = date.split("-");
        Diary diary = new Diary(null,date,Integer.parseInt(dateArr[1]),Integer.parseInt(dateArr[0]),content);
        long l = diaryDao.insert(diary);
        if (l > 0){
            RxBus.getDefault().post(new RxEvent.AddDiary(true));
            iView.finishActivity();
        }
    }
}
