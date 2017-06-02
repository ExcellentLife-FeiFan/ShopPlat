package com.ytxd.spp.model;

import com.amap.api.services.core.LatLonPoint;

import java.io.Serializable;

/**
 * Created by XY on 2017/5/31.
 */

public class HomeAddressM implements Serializable {
    String title;
    String address;
    LatLonPoint latLng;

    public HomeAddressM() {
    }

    public HomeAddressM(String title, String address, LatLonPoint latLng) {
        this.title = title;
        this.address = address;
        this.latLng = latLng;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLonPoint getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLonPoint latLng) {
        this.latLng = latLng;
    }
}
