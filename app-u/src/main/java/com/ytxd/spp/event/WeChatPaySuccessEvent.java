package com.ytxd.spp.event;

/**
 * Created by XY on 2016/11/2.
 */

public class WeChatPaySuccessEvent {
    public boolean isSuccess = true;

    public WeChatPaySuccessEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }
}
