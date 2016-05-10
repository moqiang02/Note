package com.example.rex.note.model.entity;

/**
 * Created by Rex on 2016/5/9.
 */
public class RxEvent {
    public static class DPicker{
        public int i;
        public String type;

        public DPicker(int i, String type) {
            this.i = i;
            this.type = type;
        }
    }

    public static class AddDiary{
        public boolean b;
        public AddDiary(boolean b){
            this.b = b;
        }

    }
}
