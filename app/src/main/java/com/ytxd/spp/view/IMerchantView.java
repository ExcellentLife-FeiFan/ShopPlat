package com.ytxd.spp.view;

import com.ytxd.spp.model.CatagaryM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMerchantView extends IBaseView {

    void lodeSuccess(List<CatagaryM> items);

    void lodeFailed();
}
