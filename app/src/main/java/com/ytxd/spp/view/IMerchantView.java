package com.ytxd.spp.view;

import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderGoodM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMerchantView extends IBaseView {

    void lodeSuccess(List<CatagaryM> items);

    void lodeManjianSuccess(List<MerchantM.ManJianBean> items);

    void lodeOrderGoodsSuccess(List<OrderGoodM> items);

    void lodeOrderGoodsFail();

    void lodeFailed();
}
