package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.iView.IAddDiaryView;
import com.example.rex.note.model.entity.RxEvent;
import com.example.rex.note.util.DateUtils;
import com.example.rex.note.util.RxBus;

import java.util.Calendar;


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

    public void saveDiary(String date, String diaryText, int emotion, int weather, Diary diary, String flag) {
        Calendar c = Calendar.getInstance();
        String time = "00:00:00";
        int year = 0, month = 0;
        int week = 0;
        if (flag.equals("edit")) {//修改
            diary.setContent(diaryText);
            diary.setEmotion(emotion);
            diary.setWeather(weather);
            diaryDao.update(diary);
        } else {//添加
            String currDate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
            if (date==null || date.equals(currDate)){// 没选择日期||选择了当天 =》 当天
                date = currDate;
                time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND);
                week = c.get(Calendar.DAY_OF_WEEK);
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH) + 1;
            }else{ //选择了其他日期
                String[] dateArr = date.split("-");
                year = Integer.valueOf(dateArr[0]);
                month = Integer.valueOf(dateArr[1]);
                week = DateUtils.dayForWeek(date);
            }
            Diary diaryBean = new Diary(null, date, diaryText, emotion, weather, year, month, week, time, 0);
            diaryDao.insert(diaryBean);
        }

        RxBus.getDefault().post(new RxEvent.AddDiary(true));
        iView.finishActivity();
    }
}
