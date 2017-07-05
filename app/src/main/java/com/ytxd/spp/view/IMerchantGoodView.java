package com.ytxd.spp.view;

import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.OrderGoodM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMerchantGoodView extends IBaseView {

    void lodeSuccess(List<CatagaryM> items);

    void lodeOrderGoodsSuccess(List<OrderGoodM> items);

    void lodeOrderGoodsFail();

    void lodeFailed();
}
