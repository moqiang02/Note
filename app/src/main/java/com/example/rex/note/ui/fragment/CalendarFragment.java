/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.rex.note.ui.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.R;
import com.example.rex.note.iView.ICalendarView;
import com.example.rex.note.model.entity.RxEvent;
import com.example.rex.note.presenter.CalendarPresenter;
import com.example.rex.note.ui.widget.DatePicket.bizs.calendars.DPCManager;
import com.example.rex.note.ui.widget.DatePicket.bizs.decors.DPDecor;
import com.example.rex.note.ui.widget.DatePicket.cons.DPMode;
import com.example.rex.note.ui.widget.DatePicket.views.DatePicker;
import com.example.rex.note.util.RxBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.dao.query.Query;
import rx.Subscription;
import rx.functions.Action1;

public class CalendarFragment extends BaseFragment<CalendarPresenter> implements ICalendarView {
    private Subscription rxSubscription;
    private CalendarPresenter presenter;
    private int currYear, currMonth, currDay;
    private String diaryDate;
    private Diary diary;
    private DaoSession daoSession;
    private DiaryDao diaryDao;
    private Query query;
    private List<String> dateList = new ArrayList<>();
    @Bind(R.id.main_dp)
    protected DatePicker picker;
    @Bind(R.id.button)
    protected Button button;
    @Bind(R.id.tv)
    protected TextView tv;

    @OnClick(R.id.button)
    void addClick() {
        presenter.toAddDiaryActivity(diaryDate);
    }

    @OnClick(R.id.tv)
    void showClick(TextView v){
        Bundle bundle = new Bundle();
        bundle.putSerializable("diary",diary);
        presenter.toShowDiaryActivity(bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initPresenter() {
        presenter = new CalendarPresenter(getContext(), this);
        presenter.init();
    }


    public void initView() {
        Calendar c = Calendar.getInstance();
        currYear = c.get(Calendar.YEAR);
        currMonth = c.get(Calendar.MONTH) + 1;
        currDay = c.get(Calendar.DAY_OF_MONTH);

        daoSession = App.getDaoSession();
        diaryDao = daoSession.getDiaryDao();

        // 自定义背景绘制示例 Example of custom date's background
        picker.setDate(currYear, currMonth);
        query = diaryDao.queryBuilder()
                .where(DiaryDao.Properties.Year.eq(currYear))
                .where(DiaryDao.Properties.Month.eq(currMonth))
                .build();
        List<Diary> diarys = query.list();
        for (Diary item : diarys) {
            dateList.add(item.getDate());
        }
        DPCManager.getInstance().setDecorBG(dateList);

        picker.setFestivalDisplay(true);
        picker.setTodayDisplay(true);
        picker.setHolidayDisplay(false);
        picker.setDeferredDisplay(false);
        picker.setMode(DPMode.SINGLE);
        picker.setDPDecor(new DPDecor() {
            @Override
            public void drawDecorBG(Canvas canvas, Rect rect, Paint paint) {
                paint.setColor(getResources().getColor(R.color.fen));
                canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2F, paint);
            }
        });
        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                //当前日期已有日记
                diaryDate = date;
                if (dateList.contains(date)) {
                    diary = diaryDao.queryBuilder()
                            .where(DiaryDao.Properties.Date.eq(date))
                            .build().unique();
                    if (diary != null) {
                        button.setVisibility(View.GONE);
                        tv.setVisibility(View.VISIBLE);
                        tv.setText(diary.getContent());
                    }
                } else {
                    button.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.GONE);
                }
            }
        });

        rxSubscription = RxBus.getDefault().toObserverable(RxEvent.DPicker.class)
                .subscribe(new Action1<RxEvent.DPicker>() {
                               @Override
                               public void call(RxEvent.DPicker dPicker) {
                                   int month = dPicker.month;
                                   int year = dPicker.year;
                                   query = diaryDao.queryBuilder()
                                           .where(DiaryDao.Properties.Year.eq(year))
                                           .where(DiaryDao.Properties.Month.eq(month))
                                           .build();
                                   List<Diary> diarys = query.list();
                                   dateList.clear();
                                   for (Diary item : diarys) {
                                       dateList.add(item.getDate());
                                   }
                                   DPCManager.getInstance().setDecorBG(dateList);

                                   if (dateList.contains(diaryDate)) {
                                       diary = diaryDao.queryBuilder()
                                               .where(DiaryDao.Properties.Date.eq(diaryDate))
                                               .build().unique();
                                       if (diary != null) {
                                           button.setVisibility(View.GONE);
                                           tv.setVisibility(View.VISIBLE);
                                           tv.setText(diary.getContent());
                                       }
                                   } else {
                                       button.setVisibility(View.VISIBLE);
                                       tv.setVisibility(View.GONE);
                                   }
                               }
                           },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                // TODO: 处理异常
                            }
                        });

    }


}
