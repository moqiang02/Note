package com.example.rex.note.ui.activity;

import com.example.rex.note.R;
import com.example.rex.note.iView.IAddDiaryView;
import com.example.rex.note.presenter.AddDiaryPresenter;

/**
 * Created by Rex on 2016/5/7.
 */
public class AddDiaryActivity extends ToolBarActivity implements IAddDiaryView {
    private AddDiaryPresenter presenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_diary;
    }

    @Override
    protected void initPresenter() {
        presenter = new AddDiaryPresenter(this,this);
        presenter.init();
    }

    @Override
    public void initView() {

    }
}
