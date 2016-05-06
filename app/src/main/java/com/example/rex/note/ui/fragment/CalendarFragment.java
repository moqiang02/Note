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
import android.util.Log;

import com.example.rex.note.R;
import com.example.rex.note.iView.ICalendarView;
import com.example.rex.note.model.entity.DPicker;
import com.example.rex.note.presenter.CalendarFragmentPresenter;
import com.example.rex.note.util.RxBus;
import com.example.rex.note.widget.DatePicket.bizs.calendars.DPCManager;
import com.example.rex.note.widget.DatePicket.bizs.decors.DPDecor;
import com.example.rex.note.widget.DatePicket.cons.DPMode;
import com.example.rex.note.widget.DatePicket.views.DatePicker;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

public class CalendarFragment extends BaseFragment<CalendarFragmentPresenter> implements ICalendarView {
    private Subscription rxSubscription;

    @Override
    protected int getLayoutResId() {
        return R.layout.calendar_fragment;
    }

    @Override
    protected void initPresenter() {
        presenter = new CalendarFragmentPresenter(getContext(),this);
        presenter.init();
    }


    public void initView() {
        // 自定义背景绘制示例 Example of custom date's background
        List<String> tmp = new ArrayList<>();
        tmp.add("2015-7-1");
        tmp.add("2015-7-8");
        tmp.add("2015-7-16");
        DPCManager.getInstance().setDecorBG(tmp);

        DatePicker picker = (DatePicker) view.findViewById(R.id.main_dp);
        picker.setDate(2015, 7);
        picker.setFestivalDisplay(true);
        picker.setTodayDisplay(false);
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

            }
        });

        rxSubscription = RxBus.getDefault().toObserverable(DPicker.class)
                .subscribe(new Action1<DPicker>() {
                               @Override
                               public void call(DPicker dPicker) {
                                   int i = dPicker.i;
                                   Log.d("rex", i + "**");
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
