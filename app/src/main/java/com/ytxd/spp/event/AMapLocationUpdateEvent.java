package com.ytxd.spp.event;

import com.amap.api.location.AMapLocation;

/**
 * Created by XY on 2017/5/9.
 */

public class AMapLocationUpdateEvent {
    public AMapLocation aMapLocation;

    public AMapLocationUpdateEvent(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }
}
