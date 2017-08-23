package com.ytxd.sppm.view;


/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMineFMView extends IBaseView {

    void changeStateSuccess(boolean isOpen);
    void showDialogs();
    void dismissDialogs();
}
