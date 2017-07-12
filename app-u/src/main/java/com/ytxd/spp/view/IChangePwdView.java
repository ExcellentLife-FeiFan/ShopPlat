package com.ytxd.spp.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IChangePwdView extends IBaseView {


    void stopTimer();

    void finishChange();

    void sendCodeSuccess(String code);

    void showDialogs();

    void dissmisDialogs();
}
