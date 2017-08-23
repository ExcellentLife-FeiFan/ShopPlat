package com.ytxd.spp.event;

import com.ytxd.spp.model.MerchantStateM;

/**
 * Created by XY on 2017/7/25.
 */

public class OrderChangeToCannotBuyEvent {
    public MerchantStateM stateM;

    public OrderChangeToCannotBuyEvent(MerchantStateM stateM) {
        this.stateM = stateM;
    }
}
