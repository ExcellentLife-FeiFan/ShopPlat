package com.ytxd.spp.view;

import com.ytxd.spp.model.OrderM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IHomeOrderView extends IBaseView {

    void lodeSuccess(List<OrderM> items);

    void lodeFailed();
}
