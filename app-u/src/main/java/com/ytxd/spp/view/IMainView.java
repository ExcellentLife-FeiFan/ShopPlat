package com.ytxd.spp.view;

import com.ytxd.spp.model.CouponM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMainView extends IBaseView {

    void lodeCouponSuccess(List<CouponM> items);

    void lodeCouponFailed();
}
