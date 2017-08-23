package com.ytxd.spp.view;

import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.MerchantStateM;
import com.ytxd.spp.model.OrderM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IEnsureOrderView extends IBaseView {

    void lodeAddress(AddressM address);

    void showDialogs();

    void dismissDialogs();

    void ensureOrderSuccess(OrderM orderM);

    void ensureOrderFailure(int stateCode, String msg);

    void lodeStateSucess(MerchantStateM obj, boolean isPayNow);

    void lodeStateFailure(boolean isPayNow);

    void lodeGoodsChangedSuccess(List<GoodM> goods);
}
