package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMainView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getCouponList() {
        OkGo.<ApiResult<List<CouponM>>>get(Apis.GetUserCoupon)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<List<CouponM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<CouponM>>> response) {
                        try {
                            ApiResult<List<CouponM>> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeCouponSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeCouponFailed();
                    }

                    @Override
                    public void onError(Response<ApiResult<List<CouponM>>> response) {
                        iView.lodeCouponFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }

    public void setCouponRead() {
        OkGo.<ApiResult<Object>>get(Apis.SetUserCouponAllRead)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        try {
                            ApiResult<Object> result = response.body();
                            if (result.isSuccess()) {
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.showToastShort(context, "设置优惠券已读失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        ToastUtil.showToastShort(context, "设置优惠券已读失败");
                        super.onError(response);
                    }
                });

    }


}
