package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.note.iView.IAddDiaryView;

/**
 * Created by Rex on 2016/5/7.
 */
public class AddDiaryPresenter extends BasePresenter<IAddDiaryView> {
    public AddDiaryPresenter(Context context, IAddDiaryView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        if (subscription !=null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
