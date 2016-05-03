package com.example.rex.note.presenter;

/**
 * Created by Rex on 2016/5/3.
 */

import android.content.Context;

import com.example.rex.note.iView.IBaseView;

import rx.Subscription;

public abstract class BasePresenter<T extends IBaseView> {
    protected Subscription subscription;
    protected Context context;
    protected T iView;

    public BasePresenter(Context context,T iView){
        this.context = context;
        this.iView = iView;
    }

    public void init(){
        iView.initView();
    }

    public abstract void release();
}
