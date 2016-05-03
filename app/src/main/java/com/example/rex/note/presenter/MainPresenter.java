package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.note.iView.IMainView;

/**
 * Created by Rex on 2016/5/3.
 */
public class MainPresenter extends BasePresenter<IMainView> {
    public MainPresenter(Context context,IMainView iView){
        super(context,iView);
    }

    @Override
    public void release() {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
