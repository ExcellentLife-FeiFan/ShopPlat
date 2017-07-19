package com.ytxd.spp.event;

import com.ytxd.spp.model.ArriveTimeM;

/**
 * Created by XY on 2016/11/2.
 */

public class SelectArriveTimeEvent {
    public ArriveTimeM timeM;

    public SelectArriveTimeEvent(ArriveTimeM timeM) {
        this.timeM = timeM;
    }
}
