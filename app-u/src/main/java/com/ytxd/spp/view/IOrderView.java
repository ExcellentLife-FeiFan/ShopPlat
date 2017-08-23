package com.ytxd.spp.view;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IOrderView extends IBaseView {

    void cancelSuccess(int position);

    void ensureSuccess(int position);

    void deleteSuccess(int position);

    void showDialogs();
    void dissmisDialogs();
}
