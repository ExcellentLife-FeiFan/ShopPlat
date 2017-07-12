package com.ytxd.sppm.event;

import com.ytxd.sppm.model.DeliveryStaffM;

/**
 * Created by XY on 2016/11/2.
 */

public class SelectStaffEvent {
    public int position;
    public DeliveryStaffM deliveryStaffM;

    public SelectStaffEvent(int position, DeliveryStaffM deliveryStaffM) {
        this.position = position;
        this.deliveryStaffM = deliveryStaffM;
    }
}
