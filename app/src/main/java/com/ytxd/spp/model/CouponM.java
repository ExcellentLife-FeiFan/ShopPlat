package com.ytxd.spp.model;

import java.io.Serializable;

/**
 * Created by XY on 2017/6/21.
 */

public class CouponM implements Serializable{

    /**
     * UserCouponCode : ae9eb570c8574284ab770edf528fbd14
     * CouponCode : 18
     * UserCode : 0f47b2b4906e4f0eb17512fc16939325
     * GetContent : 系统送给您18元新用户优惠券,消费满27元可使用.
     * MMoneyUse : 27
     * IsUse : 0
     * UseBeginTime : 1970-01-01T00:00:00
     * UseEndTime : 2099-01-01T00:00:00
     * IsRead : 0
     * IsDel : 0
     * CreateTime : 2017-06-21T15:29:41
     * CouponCode1 : 18
     * CouponName : 通用优惠券
     * CouponPrice : 18
     */

    private String UserCouponCode;
    private String CouponCode;
    private String UserCode;
    private String GetContent;
    private String MMoneyUse;
    private int IsUse;
    private String UseBeginTime;
    private String UseEndTime;
    private int IsRead;
    private int IsDel;
    private String CreateTime;
    private String CouponCode1;
    private String CouponName;
    private String CouponPrice;
    private boolean isPast=false;

    public String getUserCouponCode() {
        return UserCouponCode;
    }

    public void setUserCouponCode(String UserCouponCode) {
        this.UserCouponCode = UserCouponCode;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String CouponCode) {
        this.CouponCode = CouponCode;
    }

    public String getUserCode() {
        return UserCode;
    }

    public void setUserCode(String UserCode) {
        this.UserCode = UserCode;
    }

    public String getGetContent() {
        return GetContent;
    }

    public void setGetContent(String GetContent) {
        this.GetContent = GetContent;
    }

    public String getMMoneyUse() {
        return MMoneyUse;
    }

    public void setMMoneyUse(String MMoneyUse) {
        this.MMoneyUse = MMoneyUse;
    }

    public int getIsUse() {
        return IsUse;
    }

    public void setIsUse(int IsUse) {
        this.IsUse = IsUse;
    }

    public String getUseBeginTime() {
        return UseBeginTime;
    }

    public void setUseBeginTime(String UseBeginTime) {
        this.UseBeginTime = UseBeginTime;
    }

    public String getUseEndTime() {
        return UseEndTime;
    }

    public void setUseEndTime(String UseEndTime) {
        this.UseEndTime = UseEndTime;
    }

    public int getIsRead() {
        return IsRead;
    }

    public void setIsRead(int IsRead) {
        this.IsRead = IsRead;
    }

    public int getIsDel() {
        return IsDel;
    }

    public void setIsDel(int IsDel) {
        this.IsDel = IsDel;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getCouponCode1() {
        return CouponCode1;
    }

    public void setCouponCode1(String CouponCode1) {
        this.CouponCode1 = CouponCode1;
    }

    public String getCouponName() {
        return CouponName;
    }

    public void setCouponName(String CouponName) {
        this.CouponName = CouponName;
    }

    public String getCouponPrice() {
        return CouponPrice;
    }

    public void setCouponPrice(String CouponPrice) {
        this.CouponPrice = CouponPrice;
    }

    public boolean isPast() {
        return isPast;
    }

    public void setPast(boolean past) {
        isPast = past;
    }
}
