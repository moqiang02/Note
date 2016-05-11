package com.example.rex.note.ui.activity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.rex.Diary;
import com.example.rex.note.R;
import com.example.rex.note.iView.IAddDiaryView;
import com.example.rex.note.presenter.AddDiaryPresenter;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.Calendar;

import butterknife.Bind;

/**
 * Created by Rex on 2016/5/7.
 */
public class AddDiaryActivity extends ToolBarActivity implements IAddDiaryView {
    private AddDiaryPresenter presenter;
    private String date;
    @Bind(R.id.editText)
    protected EditText editText;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_diary;
    }

    @Override
    protected void initPresenter() {
        presenter = new AddDiaryPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        String flag = getIntent().getStringExtra("flag");
        if (flag.equals("add")) {
            date = (String) getIntent().getSerializableExtra("date");
        } else {
            Diary diary = (Diary) getIntent().getSerializableExtra("diary");
            date = diary.getDate();
            toolbar.setTitle(diary.getDate());
            editText.setText(diary.getContent());
        }


        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_diary:
                String diaryText = editText.getText().toString();
                if (date == null) {
                    Calendar c = Calendar.getInstance();
                    date = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
                }
                String[] dateArr = date.split("-");
                Diary diary = new Diary(null, date, Integer.valueOf(dateArr[1]), Integer.valueOf(dateArr[0]), diaryText);
                presenter.saveDiary(diary);
                break;
            case R.id.clear_content:
                editText.setText("");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }


}
