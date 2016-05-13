package com.example.rex.note.ui.activity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.example.rex.Diary;
import com.example.rex.note.R;
import com.example.rex.note.iView.IAddDiaryView;
import com.example.rex.note.presenter.AddDiaryPresenter;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Rex on 2016/5/7.
 */
public class AddDiaryActivity extends ToolBarActivity implements IAddDiaryView {
    private AddDiaryPresenter presenter;
    private Calendar c;
    private Diary diary = null;
    private String date;
    private String flag;
    private int emotion, weather;
    private int[] clickWeathers = new int[]{
            R.drawable.btn_weather_1_click, R.drawable.btn_weather_2_click, R.drawable.btn_weather_3_click,
            R.drawable.btn_weather_4_click, R.drawable.btn_weather_5_click, R.drawable.btn_weather_6_click
    };
    private int[] clickfaces = new int[]{
            R.drawable.btn_face_1_click, R.drawable.btn_face_2_click, R.drawable.btn_face_3_click,
            R.drawable.btn_face_4_click, R.drawable.btn_face_5_click, R.drawable.btn_face_6_click
    };
    private String[] week;
    @Bind(R.id.editText)
    protected EditText editText;
    @Bind(R.id.menu_yellow)
    protected FloatingActionMenu fbm;
    @Bind(R.id.weather_choice)
    protected GridView weatherChoice;
    @Bind(R.id.face_choice)
    protected GridView faceChoice;

    @OnClick(R.id.weather)
    protected void weather() {
        fbm.toggle(true);
        weatherChoice.setVisibility(View.VISIBLE);
        faceChoice.setVisibility(View.GONE);
        if (weather > 0) {
            weatherChoice.post(new Runnable() {
                @Override
                public void run() {
                    LinearLayout l = (LinearLayout) weatherChoice.getChildAt(weather);
                    ImageView img = (ImageView) l.getChildAt(0);
                    img.setImageResource(clickWeathers[weather]);
                }
            });
        }
    }

    @OnClick(R.id.emotion)
    protected void emotion() {
        fbm.toggle(true);
        weatherChoice.setVisibility(View.GONE);
        faceChoice.setVisibility(View.VISIBLE);
        if (emotion > 0) {
            faceChoice.post(new Runnable() {
                @Override
                public void run() {
                    LinearLayout l = (LinearLayout) faceChoice.getChildAt(emotion);
                    ImageView img = (ImageView) l.getChildAt(0);
                    img.setImageResource(clickfaces[emotion]);
                }
            });
        }
    }

    @OnClick(R.id.editText)
    protected void click() {
        faceChoice.setVisibility(View.GONE);
        weatherChoice.setVisibility(View.GONE);
    }

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
        week = this.getResources().getStringArray(R.array.week);
        flag = getIntent().getStringExtra("flag");
        if (flag.equals("add")) {//添加
            date = (String) getIntent().getSerializableExtra("date");
        } else {//修改
            diary = (Diary) getIntent().getSerializableExtra("diary");
            date = diary.getDate();
            toolbar.setTitle(diary.getDate() + " (" + week[diary.getWeek()] + ")");
            editText.setText(diary.getContent());
            emotion = diary.getEmotion();
            weather = diary.getWeather();
        }

        final int[] imgWeathers = new int[]{
                R.drawable.btn_create_weather_1, R.drawable.btn_create_weather_2, R.drawable.btn_create_weather_3,
                R.drawable.btn_create_weather_4, R.drawable.btn_create_weather_5, R.drawable.btn_create_weather_6
        };

        //创建一个存储Map集合的List对象
        List<Map<String, Object>> weathers = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imgWeathers.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("images", imgWeathers[i]);
            weathers.add(listItem);
        }
        final SimpleAdapter simple = new SimpleAdapter(this, weathers, R.layout.item_weather, new String[]{"images"}, new int[]{R.id.item_bg});
        //为GirdView设置Adapter
        weatherChoice.setAdapter(simple);
        //为gridView设置一个监听器,这里也可以用setOnItemSelectedListener
        weatherChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int postion, long id) {
                weather = postion;
                LinearLayout linearLayout = (LinearLayout) view;
                final ImageView img = (ImageView) linearLayout.getChildAt(0);
                simple.notifyDataSetChanged();
                view.post(new Runnable() {//TODO
                    @Override
                    public void run() {
                        img.setImageResource(clickWeathers[postion]);
                        weatherChoice.setVisibility(View.GONE);
                    }
                });
//                weatherChoice.setVisibility(View.GONE);
            }
        });
        final int[] imgfaces = new int[]{
                R.drawable.btn_create_face_1, R.drawable.btn_create_face_2, R.drawable.btn_create_face_3,
                R.drawable.btn_create_face_4, R.drawable.btn_create_face_5, R.drawable.btn_create_face_6
        };

        //创建一个存储Map集合的List对象
        List<Map<String, Object>> faces = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imgfaces.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("images", imgfaces[i]);
            faces.add(listItem);
        }
        final SimpleAdapter simple2 = new SimpleAdapter(this, faces, R.layout.item_weather, new String[]{"images"}, new int[]{R.id.item_bg});
        //为GirdView设置Adapter
        faceChoice.setAdapter(simple2);
        //为gridView设置一个监听器,这里也可以用setOnItemSelectedListener
        faceChoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int postion, long id) {
                emotion = postion;
                LinearLayout linearLayout = (LinearLayout) view;
                final ImageView img = (ImageView) linearLayout.getChildAt(0);
                simple2.notifyDataSetChanged();
                view.post(new Runnable() {//TODO
                    @Override
                    public void run() {
                        img.setImageResource(clickfaces[postion]);
                        faceChoice.setVisibility(View.GONE);
                    }
                });
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        c = Calendar.getInstance();
        switch (item.getItemId()) {
            case R.id.save_diary:
                String diaryText = editText.getText().toString();
                presenter.saveDiary(date, diaryText, emotion, weather, diary, flag);
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
