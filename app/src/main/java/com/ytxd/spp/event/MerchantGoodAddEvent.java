package com.ytxd.spp.event;

import android.view.View;

/**
 * Created by XY on 2016/11/2.
 */

public class MerchantGoodAddEvent {
    View view;

    public MerchantGoodAddEvent(View view) {
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
