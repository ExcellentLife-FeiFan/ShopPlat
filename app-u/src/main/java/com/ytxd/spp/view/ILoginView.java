package com.ytxd.spp.view;

import com.ytxd.spp.model.UserM;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface ILoginView extends IBaseView {

    void loginSuccess();
    void loginOtherSuccess(UserM userM,String type);
    void startToMain();
    void showDialogs();
    void dismissDialogs();
}
