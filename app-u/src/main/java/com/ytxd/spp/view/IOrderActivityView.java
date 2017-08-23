package com.ytxd.spp.view;

import com.ytxd.spp.model.OrderM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IOrderActivityView extends IOrderView {

    void refreshSuccess(List<OrderM> items);

    void refreshFailed();

    void lodeMoreSuccess(List<OrderM> items);

    void lodeMoreFailed();
}
