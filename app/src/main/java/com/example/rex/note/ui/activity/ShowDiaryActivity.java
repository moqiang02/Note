package com.example.rex.note.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rex.Diary;
import com.example.rex.note.R;
import com.example.rex.note.iView.IShowDiaryView;
import com.example.rex.note.presenter.ShowDiaryPresenter;
import com.example.rex.note.ui.widget.ShowDialog;

import butterknife.Bind;

/**
 * Created by Rex on 2016/5/10.
 */
public class ShowDiaryActivity extends ToolBarActivity implements IShowDiaryView {
    private ShowDiaryPresenter presenter;
    private Diary diary;
    @Bind(R.id.tv)
    protected TextView tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_show_diary;
    }

    @Override
    protected void initPresenter() {
        presenter = new ShowDiaryPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        diary = (Diary) getIntent().getSerializableExtra("diary");
        toolbar.setTitle(diary.getDate());
        tv.setText(diary.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Bundle bundle = getIntent().getExtras();
        switch (item.getItemId()) {
            case R.id.edit_diary:
                presenter.toAddDirayActivity(bundle);
                finish();
                break;
            case R.id.del_diary:
                ShowDialog.showDialog(ShowDiaryActivity.this, "确认删除日记？",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                presenter.deleteDiary(diary);
                                finish();
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
