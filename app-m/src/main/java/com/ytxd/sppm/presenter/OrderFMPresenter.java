package com.ytxd.sppm.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.model.OrderM;
import com.ytxd.sppm.net.ApiResult;
import com.ytxd.sppm.net.Apis;
import com.ytxd.sppm.net.JsonCallback;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.ToastUtil;
import com.ytxd.sppm.view.IOrderFMView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class OrderFMPresenter extends BasePresenter<IOrderFMView> {

    public OrderFMPresenter(Context context, IOrderFMView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getOrderList(final int mode, int pageIndex,String orderStateCode) {
        OkGo.<ApiResult<List<OrderM>>>get(Apis.GetCSOrderList)//
                .params("SupermarketCode", App.user.getSupermarketCode())
                .params("PageIndex", pageIndex)
                .params("OrderStateCode", orderStateCode)
                .params("PageSize", "10")
                .execute(new JsonCallback<ApiResult<List<OrderM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<OrderM>>> response) {
                        try {
                            ApiResult<List<OrderM>> result = response.body();
                            if (result.isSuccess()) {
                                List<OrderM> datas = result.getObj();
                                if (mode == CommonUtils.LODEMORE) {
                                    if (null == datas || datas.size() == 0) {
                                        iView.lodeMoreFailed();
                                    } else {
                                        iView.lodeMoreSuccess(result.getObj());
                                    }
                                } else {
                                    if (null == datas || datas.size() == 0) {
                                        iView.refreshFailed();
                                    } else {
                                        iView.refreshSuccess(datas);
                                    }
                                }
                                return;
                            }
                            ToastUtil.showToastShort(context, result.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (mode == CommonUtils.LODEMORE) {
                            iView.lodeMoreFailed();
                        } else {
                            iView.refreshFailed();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<List<OrderM>>> response) {
                        super.onError(response);
                        if (mode == CommonUtils.LODEMORE) {
                            iView.lodeMoreFailed();
                        } else {
                            iView.refreshFailed();
                        }
                        super.onError(response);
                    }
                });

    }


    public void cancel(String orderCode,String userCouponCode,String reson, final int position) {
        CommonUtils.showDialog(context);
        boolean isH=!userCouponCode.equals("0");
        OkGo.<ApiResult<Object>>get(Apis.CancelOrder)//
                .params("OrderCode", orderCode)
                .params("IsHFUserCoupon", isH?"1":"0")
                .params("UserCouponCode", userCouponCode)
                .params("CancelInfo", reson)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        CommonUtils.hideDialog();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.cancelSuccess(position);
                        } else {
                            ToastUtil.showToastShort(context, "订单取消失败:"+result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        CommonUtils.hideDialog();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }


    public void aceOrder(String orderCode, final int position) {
        CommonUtils.showDialog(context);
        OkGo.<ApiResult<Object>>get(Apis.UpdateOrderTaking)//
                .params("OrderCode", orderCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        CommonUtils.hideDialog();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.aceOrderSuccess(position);
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        CommonUtils.hideDialog();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }

    public void setSendding(String orderCode, String deliveryStaffCode, final int position) {
        CommonUtils.showDialog(context);
        OkGo.<ApiResult<Object>>get(Apis.UpdateOrderYPS)//
                .params("OrderCode", orderCode)
                .params("DeliveryStaffCode", deliveryStaffCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        CommonUtils.hideDialog();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.setSenddingSuccess(position);
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        CommonUtils.hideDialog();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }

}
