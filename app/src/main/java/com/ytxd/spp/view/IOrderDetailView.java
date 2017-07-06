package com.ytxd.spp.view;

import com.ytxd.spp.model.OrderGoodM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IOrderDetailView extends IBaseView {

    void lodeGoodsSuccess(List<OrderGoodM> items);

    void cancelSuccess();

    void ensureSuccess();
}
