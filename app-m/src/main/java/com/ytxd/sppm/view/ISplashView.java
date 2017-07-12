package com.ytxd.sppm.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface ISplashView extends IBaseView {

    void loginSuccess();
    void startToMain();
    void startToLogin();
    void startToGuide();
    void showDialogs();
    void dismissDialogs();
}
