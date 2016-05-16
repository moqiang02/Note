/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.rex.note.ui.fragment;

import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.example.rex.DaoSession;
import com.example.rex.Diary;
import com.example.rex.DiaryDao;
import com.example.rex.note.App;
import com.example.rex.note.R;
import com.example.rex.note.adapter.DiaryAdapter;
import com.example.rex.note.iView.IListView;
import com.example.rex.note.presenter.ListPresenter;
import com.example.rex.note.ui.widget.LMRecyclerView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import butterknife.Bind;
import de.greenrobot.dao.query.Query;
import rx.Subscription;

public class ListFragment extends BaseFragment<ListPresenter> implements
        SwipeRefreshLayout.OnRefreshListener, IListView, LMRecyclerView.LoadMoreListener {
    private DaoSession daoSession;
    private DiaryDao diaryDao;
    private String[] week;
    private int[] faces, weathers;
    private DiaryAdapter adapter;
    private Query query;
    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Subscription rxSubscription;
    private ArrayList<Diary> Diarys;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initPresenter() {
        presenter = new ListPresenter(getContext(), this);
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
        daoSession = App.getDaoSession();
        diaryDao = daoSession.getDiaryDao();
        Diarys = (ArrayList<Diary>) diaryDao.loadAll();
        Logger.i(Diarys.size()+"-");
        adapter = new DiaryAdapter(getContext(), Diarys, faces, weathers, week);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.yellow, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

//                presenter.fetchMeiziData(page);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


//        rxSubscription = RxBus.getDefault().toObserverable(RxEvent.AddDiary.class)
//                .subscribe(new Action1<RxEvent.AddDiary>() {
//                               @Override
//                               public void call(RxEvent.AddDiary bean) {
//                                   Diarys = (ArrayList<Diary>) diaryDao.loadAll();
//                                   adapter.notifyDataSetChanged();
//                                   recyclerView.invalidate();
//                                   Logger.i("333");
//                               }
//                           },
//                        new Action1<Throwable>() {
//                            @Override
//                            public void call(Throwable throwable) {
//                                // TODO: 处理异常
//                            }
//                        });
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void onRefresh() {
        Diarys = (ArrayList<Diary>) diaryDao.loadAll();
        adapter.notifyAll();
        Logger.i("333");
    }
}
