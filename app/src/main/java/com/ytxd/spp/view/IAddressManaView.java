package com.ytxd.spp.view;

import com.ytxd.spp.model.AddressM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IAddressManaView extends IBaseView {

    void lodeSuccess(List<AddressM> items);

    void lodeFailed();

    void deleteSuccess();
}
