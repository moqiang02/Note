package com.example.rex.note.ui.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.rex.note.R;
import com.orhanobut.logger.Logger;

import butterknife.Bind;

/**
 * Created by Rex on 2016/5/3.
 */
public abstract class ToolBarActivity extends BaseActivity {
    protected ActionBar actionBar;
    protected boolean isToolBarHiding = false;
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;
//    @Bind(R.id.app_bar)
//    protected AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar();
    }

    /**
     * 设置home icon是否可见
     *
     * @return
     */
    protected boolean canBack() {
        return true;
    }

    protected void initToolBar() {
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            Logger.d("rex", "toolbar");
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(canBack());
        } else {
            Logger.d("rex", "no- toolbar");
        }
    }

//    protected void hideOrShowToolBar() {
//        //TODO
//        appBar.animate()
//                .translationY(isToolBarHiding ? 0 : -appBar.getHeight())
//                .setInterpolator(new DecelerateInterpolator(2))
//                .start();
//        isToolBarHiding = !isToolBarHiding;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void setLeftIcon(int res) {
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(res);
        }
    }

}
