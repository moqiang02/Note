package com.example.rex.note.ui.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by Rex on 2016/5/11.
 */
public class ShowDialog {
    private static AlertDialog.Builder build = null;
    private static AlertDialog alert = null;
    public static void showDialog(Context context,String msg,DialogInterface.OnClickListener... ClickListener){
        //1.创建一个AlertDialog.Builder对象
        alert = new AlertDialog.Builder(context).create();
        //当然这里也可以这样写:build = new AlertDialog.Builder(getApplicationContext());
        //这里的话要在后面.set***吧属性设好

        //2.设置图标,标题,内容
//        alert.setIcon(R.drawable.dialog_icon);
        alert.setTitle("提示");
        alert.setMessage(msg);

        //3.设置三个按钮,可以两个或者一个,最多只能设置三个按钮哦
        //确定按钮:
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", ClickListener[0]);
        alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", ClickListener[1]);
        /*alert.setButton(DialogInterface.BUTTON_NEUTRAL, "中立", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "你点击了中立按钮", Toast.LENGTH_SHORT).show();
            }
        });*/

        //4.调用show()方法把AlertDialog显示出来
        alert.show();
    }
}
