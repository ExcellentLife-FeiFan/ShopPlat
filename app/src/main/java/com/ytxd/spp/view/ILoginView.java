package com.ytxd.spp.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface ILoginView extends IBaseView {

    void loginSuccess();
    void startToMain();
    void showDialogs();
    void dismissDialogs();
}
