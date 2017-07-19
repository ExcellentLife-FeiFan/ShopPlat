package com.ytxd.spp.model;

import com.ytxd.spp.util.AbDateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by XY on 2017/7/19.
 */

public class ArriveTimeM implements Serializable {
    public int minutesAfter;
    public long timeBegin;

    public ArriveTimeM(int minutesAfter, long timeBegin) {
        this.minutesAfter = minutesAfter;
        this.timeBegin = timeBegin;
    }

    public int getMinutesAfter() {
        return minutesAfter;
    }

    public void setMinutesAfter(int minutesAfter) {
        this.minutesAfter = minutesAfter;
    }

    public long getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
    }

    public long getTimeAfter() {
        return timeBegin + minutesAfter * 60 * 1000;
    }

    public String getTimeDesrcDay() {
        String h = AbDateUtil.getStringByFormat(getTimeAfter(), AbDateUtil.dateFormatHM);
        String bd = AbDateUtil.getStringByFormat(getTimeAfter(), AbDateUtil.dateFormatYMD);
        String nd = AbDateUtil.getStringByFormat(new Date(), AbDateUtil.dateFormatYMD);
        if (bd.equals(nd)) {
            return "今天";
        } else {
            return bd;
        }
    }
    public String getTimeDesrcDay2() {
        String h = AbDateUtil.getStringByFormat(getTimeAfter(), AbDateUtil.dateFormatHM);
        String bd = AbDateUtil.getStringByFormat(getTimeAfter(), AbDateUtil.dateFormatYMD);
        String nd = AbDateUtil.getStringByFormat(new Date(), AbDateUtil.dateFormatYMD);
        if (bd.equals(nd)) {
            return "";
        } else {
            return bd;
        }
    }

    public String getTimeDesrcHour() {
        String h = AbDateUtil.getStringByFormat(getTimeAfter(), AbDateUtil.dateFormatHM);
        return h;
    }
}
