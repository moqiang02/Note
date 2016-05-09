package com.example.rex.note.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.rex.note.iView.ICalendarView;
import com.example.rex.note.ui.activity.AddDiaryActivity;

/**
 * Created by Rex on 2016/5/6.
 */
public class CalendarPresenter extends BasePresenter<ICalendarView> {
    public CalendarPresenter(Context context, ICalendarView iView) {
        super(context, iView);
    }

    public void toAddDiaryActivity(String date) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("date",date);
        context.startActivity(intent);
    }

    @Override
    public void release() {
        if (subscription != null && subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
