package com.ytxd.spp.event;

import com.ytxd.spp.model.AddressM;

/**
 * Created by XY on 2016/11/2.
 */

public class SelectAddressEvent {
    public AddressM addressM;

    public SelectAddressEvent(AddressM addressM) {
        this.addressM = addressM;
    }
}
