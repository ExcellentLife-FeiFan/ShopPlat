package com.ytxd.spp.event;

import com.ytxd.spp.model.GoodM;

import java.util.List;

/**
 * Created by XY on 2017/7/27.
 */

public class GoodsChangeEvent {
    public List<GoodM> goods;

    public GoodsChangeEvent(List<GoodM> goods) {
        this.goods = goods;
    }
}
