package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.R;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IOrderView;

/**
 * Created by XY on 2017/7/28.
 */

public class OrderPresenter extends BasePresenter<IOrderView> {
    public OrderPresenter(Context context, IOrderView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void cancel(final int position, String orderCode, String userCouponCode, String reson) {
        iView.showDialogs();
        boolean isH = !userCouponCode.equals("0");
        OkGo.<ApiResult<Object>>get(Apis.UserCancelOrder)//
                .params("OrderCode", orderCode)
                .params("IsHFUserCoupon", isH ? "1" : "0")
                .params("UserCouponCode", userCouponCode)
                .params("CancelInfo", reson)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.cancelSuccess(position);
                        } else {
                            ToastUtil.showToastShort(context, "订单取消失败:" + result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }


    public void ensure(final int position, String orderCode) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.UserDetermineSH)//
                .params("OrderCode", orderCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.ensureSuccess(position);
                        } else {
                            ToastUtil.showToastShort(context, "收货失败:" + result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }

    public void delete(final int position, String orderCode) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.UpdateOrderYSC)//
                .params("OrderCode", orderCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.deleteSuccess(position);
                        } else {
                            ToastUtil.showToastShort(context, "删除失败:" + result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }


}
