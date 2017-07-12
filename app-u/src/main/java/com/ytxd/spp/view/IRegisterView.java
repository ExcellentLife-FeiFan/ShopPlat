package com.ytxd.spp.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IRegisterView extends IBaseView {


    void stopTimer();

    void finishRegister();

    void sendCodeSuccess(String code);

    void showDialogs();

    void dissmisDialogs();
}
