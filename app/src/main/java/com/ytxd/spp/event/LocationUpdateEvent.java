package com.ytxd.spp.event;

import com.baidu.location.BDLocation;

/**
 * Created by XY on 2017/5/9.
 */

public class LocationUpdateEvent {
    public BDLocation bdLocation;

    public LocationUpdateEvent(BDLocation bdLocation) {
        this.bdLocation = bdLocation;
    }
}
