package com.ytxd.spp.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IPayView extends IBaseView {

    void paySuccess();
    void pay();
    void showDialogs();
    void dismissDialogs();
}
