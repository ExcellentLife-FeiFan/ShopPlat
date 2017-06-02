package com.ytxd.spp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 2017/6/1.
 */

public class MerhchantGoodCategoryM implements Serializable {
    int adapterP=-1;
    String name;
    List<MerchantGoodM> goods;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MerchantGoodM> getGoods() {
        return goods;
    }

    public void setGoods(List<MerchantGoodM> goods) {
        this.goods = goods;
    }

    public int getAdapterP() {
        return adapterP;
    }

    public void setAdapterP(int adapterP) {
        this.adapterP = adapterP;
    }
}
