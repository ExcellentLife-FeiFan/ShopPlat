package com.ytxd.spp.event;

import android.view.View;

import com.ytxd.spp.model.GoodM;

/**
 * Created by XY on 2016/11/2.
 */

public class GoodAddEvent {
    public View view;
    public GoodM goodM;
    public int type;


    public GoodAddEvent(GoodM goodM,int type) {
        this.goodM = goodM;
        this.type=type;
    }
    public GoodAddEvent(View view, GoodM goodM,int type) {
        this.view = view;
        this.goodM = goodM;
        this.type=type;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public GoodM getGoodM() {
        return goodM;
    }

    public void setGoodM(GoodM goodM) {
        this.goodM = goodM;
    }
}
