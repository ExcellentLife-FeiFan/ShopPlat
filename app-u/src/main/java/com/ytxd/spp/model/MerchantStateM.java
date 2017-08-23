package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/25.
 */

public class MerchantStateM implements Serializable {

    /**
     * BusinessBeginTime : 09:00
     * BusinessEndTime : 23:00
     * ShopsOpen : 1
     */

    private String BusinessBeginTime;
    private String BusinessEndTime;
    private int ShopsOpen;

    public String getBusinessBeginTime() {
        return BusinessBeginTime;
    }

    public void setBusinessBeginTime(String BusinessBeginTime) {
        this.BusinessBeginTime = BusinessBeginTime;
    }

    public String getBusinessEndTime() {
        return BusinessEndTime;
    }

    public void setBusinessEndTime(String BusinessEndTime) {
        this.BusinessEndTime = BusinessEndTime;
    }

    public int getShopsOpen() {
        return ShopsOpen;
    }

    public void setShopsOpen(int ShopsOpen) {
        this.ShopsOpen = ShopsOpen;
    }
}
