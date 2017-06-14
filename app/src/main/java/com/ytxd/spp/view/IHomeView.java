package com.ytxd.spp.view;

import com.ytxd.spp.model.MerchantM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IHomeView extends IBaseView {

    void loginSuccess(List<MerchantM> items);

    void loginFailed();
}
