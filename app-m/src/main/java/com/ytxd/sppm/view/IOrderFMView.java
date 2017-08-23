package com.ytxd.sppm.view;


import com.ytxd.sppm.model.OrderM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IOrderFMView extends IBaseView {

    void lodeMoreFailed();

    void lodeMoreSuccess(List<OrderM> items);

    void refreshFailed();

    void refreshSuccess(List<OrderM> items);

    void aceOrderSuccess(int position);
    void aceOrderFailed(int position,OrderM orderM);

    void setSenddingSuccess(int position);

    void cancelSuccess(int position);

    void ensureSuccess(int position);

    void refundSuccess(int position);
}
