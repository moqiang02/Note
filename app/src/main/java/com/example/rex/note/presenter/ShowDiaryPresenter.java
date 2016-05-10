package com.example.rex.note.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.rex.note.iView.IShowDiaryView;
import com.example.rex.note.ui.activity.AddDiaryActivity;

/**
 * Created by Rex on 2016/5/10.
 */
public class ShowDiaryPresenter extends BasePresenter<IShowDiaryView> {
    public ShowDiaryPresenter(Context context, IShowDiaryView iView) {
        super(context, iView);
    }

    @Override
    public void release() {

    }

    public void toAddDirayActivity(Bundle bundle) {
        Intent intent = new Intent(context, AddDiaryActivity.class);
        intent.putExtra("flag","edit");
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
