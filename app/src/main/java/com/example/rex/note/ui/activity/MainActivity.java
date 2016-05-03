package com.example.rex.note.ui.activity;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.rex.note.R;
import com.example.rex.note.iView.IMainView;
import com.example.rex.note.presenter.MainPresenter;
import com.example.rex.note.ui.fragment.CheeseListFragment;
import com.example.rex.note.adapter.CategoryAdapter;

import butterknife.Bind;

/**
 * Created by Rex on 2016/5/3.
 */
public class MainActivity extends ToolBarActivity implements IMainView {
    private MainPresenter presenter;
    @Bind(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;
    @Bind(R.id.viewpager)
    protected ViewPager viewPager;
    @Bind(R.id.tabs)
    protected TabLayout tabLayout;
    @Bind(R.id.nav_view)
    protected NavigationView navigationView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this, this);
        presenter.init();
    }

    @Override
    public void initView() {
        mDrawerLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }

        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        CategoryAdapter categoryAdapter = new CategoryAdapter(getSupportFragmentManager());
        categoryAdapter.addFragment(new CheeseListFragment(), "日历");
        categoryAdapter.addFragment(new CheeseListFragment(), "列表");
        viewPager.setAdapter(categoryAdapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Log.d("rex", menuItem.getTitle() + " pressed");
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //在Action Bar的最左边，就是Home icon和标题的区域
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showNoMoreData() {

    }


}
