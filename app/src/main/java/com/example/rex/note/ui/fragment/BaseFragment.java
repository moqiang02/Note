package com.example.rex.note.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rex.note.presenter.BasePresenter;

import butterknife.ButterKnife;

/**
 * Created by Rex on 2016/5/6.
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T presenter;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // App.getRefWatcher(getContext()).watch(this);
    }
}
