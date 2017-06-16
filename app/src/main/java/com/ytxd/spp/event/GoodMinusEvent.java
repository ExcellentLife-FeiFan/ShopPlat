package com.ytxd.spp.event;

import com.ytxd.spp.model.GoodM;

/**
 * Created by XY on 2016/11/2.
 */

public class GoodMinusEvent {
    GoodM goodM;
    public int type;

    public GoodMinusEvent(GoodM goodM,int type) {
        this.goodM = goodM;
        this.type=type;
    }


    public GoodM getGoodM() {
        return goodM;
    }

    public void setGoodM(GoodM goodM) {
        this.goodM = goodM;
    }
}
