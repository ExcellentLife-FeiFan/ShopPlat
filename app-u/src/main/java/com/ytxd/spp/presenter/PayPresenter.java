package com.ytxd.spp.presenter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.model.PayM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.util.alipay.PayResult;
import com.ytxd.spp.view.IPayView;

import java.util.Map;

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

    @SuppressLint("HandlerLeak")
    public Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        ToastUtil.showToastShort(context, "支付宝付款成功");
                        iView.paySuccess();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.showToastShort(context, "支付取消或失败");
                    }
                    break;
                }

            }
        }
    };

    public void pay(final Activity activity, OrderM order, final String payType, final IWXAPI api) {
        iView.showDialogs();
        GetRequest getRequest = OkGo.<ApiResult<PayM>>get(Apis.Pay)//
                .params("PayType", payType)
                .params("OrderCode", order.getOrderCode())
                .params("AndroidOrIos", order.getSuperMarketModel().getAndroidOrIos())
                .params("TSAlias", order.getSuperMarketModel().getTSAlias());
        if (payType.equals("0002")) {
            String device_info = CommonUtils.getImei(activity);
            String spbill_create_ip = CommonUtils.getHostIP();
            getRequest.params("device_info", device_info)
                    .params("spbill_create_ip", spbill_create_ip);
        }
        getRequest.execute(new JsonCallback<ApiResult<PayM>>() {
            @Override
            public void onSuccess(Response<ApiResult<PayM>> response) {
                iView.dismissDialogs();
                try {
                    final ApiResult<PayM> result = response.body();
                    if (result.isSuccess()) {
                        switch (payType) {
                            case "0001":
                                payAlipay(result.getObj().getPayStr(), activity);
                                break;
                            case "0002":
                                payWechat(result.getObj(), activity, api);
                                break;
                            case "0003":
                                break;
                        }
                    } else {
                        iView.payFailure();
                    }
                } catch (Exception e) {
                    ToastUtil.showToastShort(context, "支付失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Response<ApiResult<PayM>> response) {
                ToastUtil.showToastShort(context, "支付失败");
                iView.dismissDialogs();
                super.onError(response);
            }
        });

    }


    public void payAlipay(final String paycode, final Activity context) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(context);
                Map<String, String> resultD = alipay.payV2(paycode, true);
                Log.i("msp", resultD.toString());
                Message msg = new Message();
                msg.what = 1;
                msg.obj = resultD;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    public void payWechat(PayM payM, final Activity context, IWXAPI api) {
        PayReq req = new PayReq();
        req.appId = payM.getWx_Result().getAppid();
        req.partnerId = payM.getWx_Result().getPartnerid();
        req.prepayId = payM.getWx_Result().getPrepayid();
        req.nonceStr = payM.getWx_Result().getNoncestr();
        req.timeStamp = payM.getWx_Result().getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = payM.getWx_Result().getSign();
//        req.extData = "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        boolean is = api.sendReq(req);
        if (!is) {
            ToastUtil.showToastShort(context, "调起支付失败");
        }

    }


}
