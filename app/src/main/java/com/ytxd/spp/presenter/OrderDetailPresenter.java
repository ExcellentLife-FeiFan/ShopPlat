package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.R;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IOrderDetailView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class OrderDetailPresenter extends BasePresenter<IOrderDetailView> {

    public OrderDetailPresenter(Context context, IOrderDetailView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getGoodsInfo(String OrderCode) {
        OkGo.<ApiResult<List<OrderGoodM>>>get(Apis.GetOrderGoodsInfo)//
                .params("OrderCode", OrderCode)
                .execute(new JsonCallback<ApiResult<List<OrderGoodM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<OrderGoodM>>> response) {
                        ApiResult<List<OrderGoodM>> result = response.body();
                        if (result.isSuccess()) {
                            iView.lodeGoodsSuccess(result.getObj());
                        } else {
                            iView.lodeGoodsSuccess(null);
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<List<OrderGoodM>>> response) {
                        iView.lodeGoodsSuccess(null);
                        super.onError(response);
                    }
                });

    }


    public void cancel(String orderCode,String userCouponCode) {
        OkGo.<ApiResult<Object>>get(Apis.CancelOrder)//
                .params("OrderCode", orderCode)
                .params("UserCouponCode", userCouponCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.cancelSuccess();
                        } else {
                            ToastUtil.showToastShort(context, "订单取消失败:"+result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }
}
