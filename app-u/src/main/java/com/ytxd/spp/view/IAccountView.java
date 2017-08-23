package com.ytxd.spp.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IAccountView extends IBaseView {

    void showDialogs();

    void dismissDialogs();

    void changeIconSuccess();

    void changeNicknameSuccess();

    void bindThirdPartSuccess(String type,String code);
}
