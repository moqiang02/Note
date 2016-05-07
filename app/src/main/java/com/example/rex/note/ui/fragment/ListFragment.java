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

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;

import com.example.rex.note.R;
import com.example.rex.note.adapter.DiaryAdapter;
import com.example.rex.note.iView.IListView;
import com.example.rex.note.model.entity.Diary;
import com.example.rex.note.presenter.ListPresenter;
import com.example.rex.note.ui.widget.LMRecyclerView;

import java.util.ArrayList;

import butterknife.Bind;

public class ListFragment extends BaseFragment<ListPresenter> implements
        SwipeRefreshLayout.OnRefreshListener, IListView, LMRecyclerView.LoadMoreListener {
    @Bind(R.id.recycler_view)
    LMRecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.list_fragment;
    }

    @Override
    protected void initPresenter() {
        presenter = new ListPresenter(getContext(),this);
        presenter.init();
    }


    @Override
    public void initView() {
        ArrayList<Diary> Diarys = new ArrayList<>();
        Diary diary1 = new Diary("1","aaa");
        Diary diary2 = new Diary("2","bbb");
        Diary diary3 = new Diary("3","ccc");
        Diary diary4 = new Diary("4","ddd");
        Diarys.add(diary1);
        Diarys.add(diary2);
        Diarys.add(diary3);
        Diarys.add(diary4);
        DiaryAdapter adapter = new DiaryAdapter(getContext(), Diarys);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLoadMoreListener(this);
    }

    @Override
    public void loadMore() {

    }

    @Override
    public void onRefresh() {

    }
}
