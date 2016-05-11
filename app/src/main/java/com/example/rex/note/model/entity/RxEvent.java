package com.example.rex.note.model.entity;

/**
 * Created by Rex on 2016/5/9.
 */
public class RxEvent {
    public static class DPicker{
        public int month;
        public int year;

        public DPicker(int month, int year) {
            this.month = month;
            this.year = year;
        }
    }

    public static class AddDiary{
        public boolean b;
        public AddDiary(boolean b){
            this.b = b;
        }

    }

    public static class DeleteDiary {
        public boolean b;
        public DeleteDiary(boolean b){
            this.b = b;
        }
    }
}
