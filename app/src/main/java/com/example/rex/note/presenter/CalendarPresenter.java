package com.example.rex.note.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.iView.ICalendarView;
import com.example.rex.note.ui.activity.AddDiaryActivity;
import com.example.rex.note.ui.activity.ShowDiaryActivity;

import java.util.Calendar;
import java.util.List;

import de.greenrobot.dao.query.Query;

/**
 * Created by Rex on 2016/5/6.
 */
public class CalendarPresenter extends BasePresenter<ICalendarView> {
    private DaoSession daoSession;
    private DiaryDao diaryDao;
    private Query query;

    public CalendarPresenter(Context context, ICalendarView iView) {
        super(context, iView);
        daoSession = App.getDaoSession();
        diaryDao = daoSession.getDiaryDao();
    }

    public void toAddDiaryActivity(String date) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("flag", "add");
        intent.putExtra("date", date);
        context.startActivity(intent);
    }

    public List<Diary> getDiariesByYM(int year,int month) {

        query = diaryDao.queryBuilder()
                .where(DiaryDao.Properties.Year.eq(year))
                .where(DiaryDao.Properties.Month.eq(month))
                .build();
        return query.list();
    }

    @Override
    public void release() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void toShowDiaryActivity(Bundle bundle) {
        Intent intent = new Intent(context, ShowDiaryActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public Diary getDiaryByDate(String date) {
        Diary diary = diaryDao.queryBuilder()
                .where(DiaryDao.Properties.Date.eq(date))
                .build().unique();
        return diary;
    }
}
