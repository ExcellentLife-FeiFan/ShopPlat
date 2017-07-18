package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IPayView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class PayPresenter extends BasePresenter<IPayView> {

    public PayPresenter(Context context, IPayView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }


    public void payUpload(OrderM orderM) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.UpdateOrderPay)//
                .params("OrderCode", orderM.getOrderCode())
                .params("AndroidOrIos", orderM.getSuperMarketModel().getAndroidOrIos())
                .params("TSAlias", orderM.getSuperMarketModel().getTSAlias())
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        try {
                            ApiResult<Object> result = response.body();
                            if (result.isSuccess()) {
                                iView.paySuccess();
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.showToastShort(context, "支付失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ToastUtil.showToastShort(context, "支付失败");
                        super.onError(response);
                    }
                });




    }

}
