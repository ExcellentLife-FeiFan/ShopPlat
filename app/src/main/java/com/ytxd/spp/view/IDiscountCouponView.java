package com.ytxd.spp.view;

import com.ytxd.spp.model.CouponM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IDiscountCouponView extends IBaseView {

    void lodeSuccess(List<CouponM> items);

    void lodeFailed();
}
