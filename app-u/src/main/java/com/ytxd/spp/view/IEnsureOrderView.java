package com.ytxd.spp.view;

import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.model.OrderM;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IEnsureOrderView extends IBaseView {

    void lodeAddress(AddressM address);

    void showDialogs();

    void dismissDialogs();

    void ensureOrderSuccess(OrderM orderM);
}
