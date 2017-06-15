package com.ytxd.spp.event;

import com.ytxd.spp.model.GoodM;

/**
 * Created by XY on 2016/11/2.
 */

public class MerchantGoodMinusEvent {
    GoodM goodM;

    public MerchantGoodMinusEvent(GoodM goodM) {
        this.goodM = goodM;
    }

    public GoodM getGoodM() {
        return goodM;
    }

    public void setGoodM(GoodM goodM) {
        this.goodM = goodM;
    }
}
