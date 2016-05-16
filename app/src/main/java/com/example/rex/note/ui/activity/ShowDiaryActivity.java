package com.example.rex.note.ui.activity;

import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
    private int[] faces, weathers;
    private String[] week;
    @Bind(R.id.tv)
    protected TextView tv;
    @Bind(R.id.emotion_m)
    protected ImageView emotion;
    @Bind(R.id.weather_m)
    protected ImageView weather;
    @Bind(R.id.time_m)
    protected TextView time;

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
        week = getResources().getStringArray(R.array.week);
        TypedArray typedArray = getResources().obtainTypedArray(R.array.faces);
        faces = new int[typedArray.length()];
        for (int index = 0; index < typedArray.length(); index++) {
            faces[index] = typedArray.getResourceId(index, 0);
        }
        typedArray = getResources().obtainTypedArray(R.array.weathers);
        weathers = new int[typedArray.length()];
        for (int index = 0; index < typedArray.length(); index++) {
            weathers[index] = typedArray.getResourceId(index, 0);
        }
//        weathers = getResources().getIntArray(R.array.weathers);
        diary = (Diary) getIntent().getSerializableExtra("diary");
        toolbar.setTitle(diary.getDate() + " (" + week[diary.getWeek()] + ")");
        tv.setText(diary.getContent());
        emotion.setImageResource(faces[diary.getEmotion()]);
        weather.setImageResource(weathers[diary.getWeather()]);
        time.setText(diary.getTime());
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
