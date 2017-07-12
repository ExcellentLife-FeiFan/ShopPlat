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
import com.ytxd.spp.view.IDiscountCouponView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class DiscountCouponPresenter extends BasePresenter<IDiscountCouponView> {

    public DiscountCouponPresenter(Context context, IDiscountCouponView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getList() {
        OkGo.<ApiResult<List<CouponM>>>get(Apis.GetUserCoupon)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<List<CouponM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<CouponM>>> response) {
                        try {
                            ApiResult<List<CouponM>> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeFailed();
                    }

                    @Override
                    public void onError(Response<ApiResult<List<CouponM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


}
