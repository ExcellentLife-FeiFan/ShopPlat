package com.ytxd.sppm.event;

import com.ytxd.sppm.model.OrderM;

/**
 * Created by XY on 2016/11/2.
 */

public class AceOrderSuccessEvent {
    public OrderM orderM;

    public AceOrderSuccessEvent(OrderM orderM) {
        this.orderM = orderM;
    }
}
