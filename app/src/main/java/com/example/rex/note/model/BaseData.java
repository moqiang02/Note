package com.example.rex.note.model;

import java.io.Serializable;

/**
 * Created by Rex on 2016/5/4.
 */
public class BaseData implements Serializable {
    public boolean error;

    @Override
    public String toString() {
        return "BaseData{" +
                "error=" + error +
                '}';
    }
}