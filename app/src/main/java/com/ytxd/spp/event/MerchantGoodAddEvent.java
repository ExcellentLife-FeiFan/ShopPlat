package com.ytxd.spp.event;

import android.view.View;

import com.ytxd.spp.model.GoodM;

/**
 * Created by XY on 2016/11/2.
 */

public class MerchantGoodAddEvent {
    public View view;
    public GoodM goodM;

    public MerchantGoodAddEvent(GoodM goodM) {
        this.goodM = goodM;
    }

    public MerchantGoodAddEvent(View view, GoodM goodM) {
        this.view = view;
        this.goodM = goodM;
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
