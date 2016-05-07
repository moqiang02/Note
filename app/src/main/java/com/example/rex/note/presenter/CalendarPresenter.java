package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.note.iView.ICalendarView;

/**
 * Created by Rex on 2016/5/6.
 */
public class CalendarPresenter extends BasePresenter<ICalendarView> {
    public CalendarPresenter(Context context, ICalendarView iView){
        super(context, iView);
    }

    @Override
    public void release() {
        if (subscription !=null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
