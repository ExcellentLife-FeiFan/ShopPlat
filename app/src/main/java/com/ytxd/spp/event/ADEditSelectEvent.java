package com.ytxd.spp.event;

import com.amap.api.services.core.PoiItem;

/**
 * Created by XY on 2016/11/2.
 */

public class ADEditSelectEvent {
    public PoiItem poiItem;

    public ADEditSelectEvent(PoiItem poiItem) {
        this.poiItem = poiItem;
    }
}
