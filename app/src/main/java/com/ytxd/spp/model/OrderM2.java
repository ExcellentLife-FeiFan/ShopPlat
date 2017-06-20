package com.ytxd.spp.model;

/**
 * Created by XY on 2017/6/20.
 */

public class OrderM2 extends OrderM {

    private String GoodsCode;
    private String GoodsTypeCode;
    private String GoodsTitle;
    private String Content;
    private String LogoPaths;
    private String YPrice1;
    private String XPrice;
    private int BuyNumber;

    public String getGoodsCode() {
        return GoodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        GoodsCode = goodsCode;
    }

    public String getGoodsTypeCode() {
        return GoodsTypeCode;
    }

    public void setGoodsTypeCode(String goodsTypeCode) {
        GoodsTypeCode = goodsTypeCode;
    }

    public String getGoodsTitle() {
        return GoodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        GoodsTitle = goodsTitle;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getLogoPaths() {
        return LogoPaths;
    }

    public void setLogoPaths(String logoPaths) {
        LogoPaths = logoPaths;
    }

    public String getYPrice1() {
        return YPrice1;
    }

    public void setYPrice1(String YPrice1) {
        this.YPrice1 = YPrice1;
    }

    public String getXPrice() {
        return XPrice;
    }

    public void setXPrice(String XPrice) {
        this.XPrice = XPrice;
    }

    public int getBuyNumber() {
        return BuyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        BuyNumber = buyNumber;
    }
}
