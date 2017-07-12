package com.ytxd.sppm.view;


import com.ytxd.sppm.model.DeliveryStaffM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface ISelectDeliveryStaffView extends IBaseView {

    void lodeFailed();

    void lodeSuccess(List<DeliveryStaffM> items);

}
