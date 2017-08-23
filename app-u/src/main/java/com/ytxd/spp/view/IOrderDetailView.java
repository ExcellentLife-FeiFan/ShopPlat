package com.ytxd.spp.view;

import com.ytxd.spp.model.OrderM;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IOrderDetailView extends IOrderView {

    void lodeOrderSuccess(OrderM order);

}
