package com.example.rex.note.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.rex.note.iView.ICalendarView;
import com.example.rex.note.ui.activity.AddDiaryActivity;
import com.example.rex.note.ui.activity.ShowDiaryActivity;

/**
 * Created by Rex on 2016/5/6.
 */
public class CalendarPresenter extends BasePresenter<ICalendarView> {
    public CalendarPresenter(Context context, ICalendarView iView) {
        super(context, iView);
    }

    public void toAddDiaryActivity(String date) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("flag","add");
        intent.putExtra("date",date);
        context.startActivity(intent);
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
}
