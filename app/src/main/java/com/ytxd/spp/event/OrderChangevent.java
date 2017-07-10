package com.ytxd.spp.event;

/**
 * Created by XY on 2016/11/2.
 */

public class OrderChangevent {
    public int position;
    public String state;
    public boolean isEvaluate=false;

    public OrderChangevent(int position, String state) {
        this.position = position;
        this.state = state;
    }

    public OrderChangevent(int position, boolean isEvaluate) {
        this.position = position;
        this.isEvaluate = isEvaluate;
    }
}
