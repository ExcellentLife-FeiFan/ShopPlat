package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/7/17.
 */

public class JpushNotifyM implements Serializable {
    public String ALERT;
    public String EXTRA;
    public String NOTIFICATION_ID;
    public String ALERT_TYPE;
    public String NOTIFICATION_CONTENT_TITLE;
    public String MSG_ID;
    public String MESSAGE;

    public String getALERT() {
        return ALERT;
    }

    public void setALERT(String ALERT) {
        this.ALERT = ALERT;
    }

    public String getEXTRA() {
        return EXTRA;
    }

    public void setEXTRA(String EXTRA) {
        this.EXTRA = EXTRA;
    }

    public String getNOTIFICATION_ID() {
        return NOTIFICATION_ID;
    }

    public void setNOTIFICATION_ID(String NOTIFICATION_ID) {
        this.NOTIFICATION_ID = NOTIFICATION_ID;
    }

    public String getALERT_TYPE() {
        return ALERT_TYPE;
    }

    public void setALERT_TYPE(String ALERT_TYPE) {
        this.ALERT_TYPE = ALERT_TYPE;
    }

    public String getNOTIFICATION_CONTENT_TITLE() {
        return NOTIFICATION_CONTENT_TITLE;
    }

    public void setNOTIFICATION_CONTENT_TITLE(String NOTIFICATION_CONTENT_TITLE) {
        this.NOTIFICATION_CONTENT_TITLE = NOTIFICATION_CONTENT_TITLE;
    }

    public String getMSG_ID() {
        return MSG_ID;
    }

    public void setMSG_ID(String MSG_ID) {
        this.MSG_ID = MSG_ID;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }
}
