package com.example.rex.note.presenter;

import android.content.Context;

import com.example.rex.note.iView.IListView;

/**
 * Created by Rex on 2016/5/6.
 */
public class ListPresenter extends BasePresenter<IListView> {
    public ListPresenter(Context context, IListView iView){
        super(context, iView);
    }

    @Override
    public void release() {
        if (subscription !=null && subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
