package com.example.rex.note.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rex.Diary;
import com.example.rex.note.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rex on 2016/5/6.
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryHolder> {

    List<Diary> list;
    Context context;
    int lastPosition = 0;
    int[] faces, weathers;
    String[] week;


    public DiaryAdapter(Context context, List<Diary> list, int[] faces, int[] weathers,String[] week) {
        this.list = list;
        this.context = context;
        this.faces = faces;
        this.weathers = weathers;
        this.week = week;
    }

    @Override
    public DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary, parent, false);
        return new DiaryHolder(view);
    }

    @Override
    public void onBindViewHolder(DiaryHolder holder, int position) {
        Diary diary = list.get(position);
        holder.card.setTag(diary);
        holder.date.setText(diary.getDate() + " (" + week[diary.getWeek()] + ")");
        holder.emotion.setImageResource(faces[diary.getEmotion()]);
        holder.weather.setImageResource(weathers[diary.getWeather()]);
        holder.content.setText(diary.getContent().substring(0,40)+"...");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private void showItemAnimation(DiaryHolder holder, int position) {
        if (position > lastPosition) {
            lastPosition = position;
            ObjectAnimator.ofFloat(holder.card, "translationY", 1f * holder.card.getHeight(), 0f)
                    .setDuration(500)
                    .start();
        }
    }

    class DiaryHolder extends RecyclerView.ViewHolder {
        View card;
        @Bind(R.id.date)
        TextView date;
        @Bind(R.id.content)
        TextView content;
        @Bind(R.id.emotion_l)
        ImageView emotion;
        @Bind(R.id.weather_l)
        ImageView weather;

        public DiaryHolder(View itemView) {
            super(itemView);
            card = itemView;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.card_lv)
        void DiaryClick() {

        }


    }
}
