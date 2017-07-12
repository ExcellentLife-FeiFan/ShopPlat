package com.ytxd.spp.event;

import com.ytxd.spp.model.GoodM;

/**
 * Created by XY on 2016/11/2.
 */

public class MerchantSelectGoodStandEvent {
    public GoodM goodM;

    public MerchantSelectGoodStandEvent(GoodM goodM) {
        this.goodM = goodM;
    }
}
