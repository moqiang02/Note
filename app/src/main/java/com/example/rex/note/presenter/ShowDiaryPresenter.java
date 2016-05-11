package com.example.rex.note.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.iView.IShowDiaryView;
import com.example.rex.note.model.entity.RxEvent;
import com.example.rex.note.ui.activity.AddDiaryActivity;
import com.example.rex.note.util.RxBus;

/**
 * Created by Rex on 2016/5/10.
 */
public class ShowDiaryPresenter extends BasePresenter<IShowDiaryView> {
    private DaoSession daoSession;
    private DiaryDao diaryDao;

    public ShowDiaryPresenter(Context context, IShowDiaryView iView) {
        super(context, iView);
        daoSession = App.getDaoSession();
        diaryDao = daoSession.getDiaryDao();
    }

    @Override
    public void release() {

    }

    public void toAddDirayActivity(Bundle bundle) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("flag", "edit");
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void deleteDiary(Diary diary) {
        diaryDao.deleteByKey(diary.getId());
        RxBus.getDefault().post(new RxEvent.DeleteDiary(true));
    }
}
