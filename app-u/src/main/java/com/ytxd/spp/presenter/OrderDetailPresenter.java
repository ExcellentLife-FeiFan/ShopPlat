package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IOrderDetailView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class OrderDetailPresenter extends OrderPresenter {
    IOrderDetailView iView;

    public OrderDetailPresenter(Context context, IOrderDetailView iView) {
        super(context, iView);
        this.iView = iView;
    }

    @Override
    public void release() {
    }

    public void getOrderInfo(String OrderCode) {
        OkGo.<ApiResult<OrderM>>get(Apis.GetOrderInfo)//
                .params("OrderCode", OrderCode)
                .execute(new JsonCallback<ApiResult<OrderM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<OrderM>> response) {
                        try {
                            ApiResult<OrderM> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeOrderSuccess(result.getObj());
                            } else {
                                iView.lodeOrderSuccess(null);
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.showToastShort(context, "异常");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<OrderM>> response) {
                        iView.lodeOrderSuccess(null);
                        super.onError(response);
                    }
                });

    }

}
