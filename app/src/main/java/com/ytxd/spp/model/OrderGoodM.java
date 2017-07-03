package com.ytxd.spp.model;

/**
 * Created by XY on 2017/6/15.
 */

public class OrderGoodM extends GoodM {


    /**
     * OrderGoodsCode : ee7519c8524444b2aab568e01465fe4b
     * OrderCode : f5a4bf0e89c3407d85ce12b479da03d0
     * BuyNumber : 4
     * OrderBy : 1
     * CreateTime : 2017-07-03T10:52:43
     */

    private String OrderGoodsCode;
    private String OrderCode;
    private int BuyNumber;
    private int OrderBy;
    private String CreateTime;

    public String getOrderGoodsCode() {
        return OrderGoodsCode;
    }

    public void setOrderGoodsCode(String OrderGoodsCode) {
        this.OrderGoodsCode = OrderGoodsCode;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String OrderCode) {
        this.OrderCode = OrderCode;
    }

    public int getBuyNumber() {
        return BuyNumber;
    }

    public void setBuyNumber(int BuyNumber) {
        this.BuyNumber = BuyNumber;
    }

    public int getOrderBy() {
        return OrderBy;
    }

    public void setOrderBy(int OrderBy) {
        this.OrderBy = OrderBy;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }
}
