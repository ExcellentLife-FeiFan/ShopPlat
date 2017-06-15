package com.ytxd.spp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XY on 2017/6/15.
 */

public class CatagaryM implements Serializable {

    /**
     * GoodsTypeCode : 82f8c4e0011c4079a7d9b483e98127f6
     * GoodsTypeName : 美团
     * Children : []
     */
    int adapterP=-1;
    private String GoodsTypeCode;
    private String GoodsTypeName;
    private List<GoodM> Children;

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

    public List<GoodM> getChildren() {
        return Children;
    }

    public void setChildren(List<GoodM> Children) {
        this.Children = Children;
    }
    public int getAdapterP() {
        return adapterP;
    }

    public void setAdapterP(int adapterP) {
        this.adapterP = adapterP;
    }
}
