package com.ytxd.sppm.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2017/6/15.
 */

public class GoodM implements Serializable {

    /**
     * GoodsTypeCode : 82f8c4e0011c4079a7d9b483e98127f6
     * GoodsTypeName : 美团
     * GoodsCode : a9439937bb514d9c9ad7e239cf99e272
     * GoodsTitle : 西瓜
     * Content : 不甜不要钱
     * LogoPaths : /File/UpFiles/s.jpg
     * YPrice : 12.00
     * XPrice : 9.10
     * SaleNumber : 0
     * IsSpec : 0
     * ParentCode : 0
     * Goods : []
     */

    int adapterP=-1;
    private String GoodsTypeCode;
    private String GoodsTypeName;
    private String GoodsCode;
    private String GoodsTitle;
    private String Content;
    private String LogoPaths;
    private String YPrice;
    private String XPrice;
    private int SaleNumber;
    private int IsSpec;
    private String ParentCode;
    private List<GoodM> Goods;

    public String getGoodsTypeCode() {
        return GoodsTypeCode;
    }

    public void setGoodsTypeCode(String GoodsTypeCode) {
        this.GoodsTypeCode = GoodsTypeCode;
    }

    public String getGoodsTypeName() {
        return GoodsTypeName;
    }

    public void setGoodsTypeName(String GoodsTypeName) {
        this.GoodsTypeName = GoodsTypeName;
    }

    public String getGoodsCode() {
        return GoodsCode;
    }

    public void setGoodsCode(String GoodsCode) {
        this.GoodsCode = GoodsCode;
    }

    public String getGoodsTitle() {
        return GoodsTitle;
    }

    public void setGoodsTitle(String GoodsTitle) {
        this.GoodsTitle = GoodsTitle;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getLogoPaths() {
        return LogoPaths;
    }

    public void setLogoPaths(String LogoPaths) {
        this.LogoPaths = LogoPaths;
    }

    public String getYPrice() {
        return YPrice;
    }

    public void setYPrice(String YPrice) {
        this.YPrice = YPrice;
    }

    public String getXPrice() {
        return XPrice;
    }

    public void setXPrice(String XPrice) {
        this.XPrice = XPrice;
    }

    public int getSaleNumber() {
        return SaleNumber;
    }

    public void setSaleNumber(int SaleNumber) {
        this.SaleNumber = SaleNumber;
    }

    public int getIsSpec() {
        return IsSpec;
    }

    public void setIsSpec(int IsSpec) {
        this.IsSpec = IsSpec;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String ParentCode) {
        this.ParentCode = ParentCode;
    }

    public List<GoodM> getGoods() {
        return Goods;
    }

    public void setGoods(List<GoodM> Goods) {
        this.Goods = Goods;
    }

    public int getAdapterP() {
        return adapterP;
    }

    public void setAdapterP(int adapterP) {
        this.adapterP = adapterP;
    }
}
