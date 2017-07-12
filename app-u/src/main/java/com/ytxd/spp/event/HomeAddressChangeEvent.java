package com.ytxd.spp.event;

import com.ytxd.spp.model.HomeAddressM;

/**
 * Created by XY on 2016/11/2.
 */

public class HomeAddressChangeEvent {
    HomeAddressM addressM;

    public HomeAddressChangeEvent(HomeAddressM addressM) {
        this.addressM = addressM;
    }

    public HomeAddressM getAddressM() {
        return addressM;
    }

    public void setAddressM(HomeAddressM addressM) {
        this.addressM = addressM;
    }
}
