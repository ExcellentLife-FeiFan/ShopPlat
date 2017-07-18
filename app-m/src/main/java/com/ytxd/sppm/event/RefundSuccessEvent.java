package com.ytxd.sppm.event;

import com.ytxd.sppm.model.OrderM;

/**
 * Created by XY on 2016/11/2.
 */

public class RefundSuccessEvent {
    public OrderM orderM;

    public RefundSuccessEvent(OrderM orderM) {
        this.orderM = orderM;
    }
}
