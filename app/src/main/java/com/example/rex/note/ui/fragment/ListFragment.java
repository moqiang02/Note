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

import com.example.rex.Diary;
import com.example.rex.note.R;
import com.example.rex.note.adapter.DiaryAdapter;
import com.example.rex.note.iView.IListView;
import com.example.rex.note.presenter.ListPresenter;
import com.example.rex.note.ui.widget.LMRecyclerView;
import com.example.rex.note.ui.widget.ShowToast;

import java.util.ArrayList;

import butterknife.Bind;

public class ListFragment extends BaseFragment<ListPresenter> implements
        SwipeRefreshLayout.OnRefreshListener, IListView, LMRecyclerView.LoadMoreListener {
    private String[] week;
    private int[] faces, weathers;
    private DiaryAdapter adapter;
    private int page = 1;
    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Diary> diaryList = new ArrayList<>();
    private boolean isRefresh = true;
    private boolean canLoading = true;

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
        adapter = new DiaryAdapter(getContext(), diaryList, faces, weathers, week);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.yellow, R.color.colorAccent, R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                showProgress();
                presenter.fetchDiaryData(page);
            }
        });


    }

    @Override
    public void loadMore() {
        if (canLoading) {
            presenter.fetchDiaryData(page);
            canLoading = false;
        }
    }

    @Override
    public void onRefresh() {
        isRefresh = true;
        page = 1;
        presenter.fetchDiaryData(page);
    }

    @Override
    public void showDiaryList(ArrayList<Diary> diarys) {
        canLoading = true;
        page++;
        if (isRefresh) {
            diaryList.clear();
            diaryList.addAll(diarys);
            adapter.notifyDataSetChanged();//不使用addAll()的话，notifyDataSetChanged()无效
            isRefresh = false;
        } else {
            diaryList.addAll(diarys);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNoMoreData() {
        canLoading = false;
        ShowToast.showShort("全部加载完毕");

    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
