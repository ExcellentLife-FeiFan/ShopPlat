package com.ytxd.spp.view;

import com.ytxd.spp.model.MerchantM;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMerchantView extends IBaseView {

  /*  void lodeManjianSuccess(List<MerchantM.ManJianBean> items);

    void lodeStateSucess(MerchantStateM obj);*/

    void lodeInfoSucess(MerchantM merchantM, boolean isLodeGood);

    void lodeInfoFailure(boolean isLodeGood);
}
