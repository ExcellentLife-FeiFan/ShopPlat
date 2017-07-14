package com.ytxd.sppm.event;

import com.ytxd.sppm.model.OrderM;

/**
 * Created by XY on 2016/11/2.
 */

public class CancelSuccessEvent {
    public OrderM orderM;

    public CancelSuccessEvent(OrderM orderM) {
        this.orderM = orderM;
    }
}
