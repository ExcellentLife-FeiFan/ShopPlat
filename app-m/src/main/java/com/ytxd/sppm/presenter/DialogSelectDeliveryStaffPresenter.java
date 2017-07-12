package com.ytxd.sppm.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.model.DeliveryStaffM;
import com.ytxd.sppm.net.ApiResult;
import com.ytxd.sppm.net.Apis;
import com.ytxd.sppm.net.JsonCallback;
import com.ytxd.sppm.util.ToastUtil;
import com.ytxd.sppm.view.ISelectDeliveryStaffView;

import java.util.List;


/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class DialogSelectDeliveryStaffPresenter extends BasePresenter<ISelectDeliveryStaffView>{


    public DialogSelectDeliveryStaffPresenter(Context context, ISelectDeliveryStaffView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getList() {
        OkGo.<ApiResult<List<DeliveryStaffM>>>get(Apis.GetCSPSStaff)//
                .cacheKey("SelectDeliveryStaffDialog")
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .cacheTime(1000 * 60 * 60 * 1)
                .params("SupermarketCode", App.user.getSupermarketCode())
                .execute(new JsonCallback<ApiResult<List<DeliveryStaffM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<DeliveryStaffM>>> response) {
                        ApiResult<List<DeliveryStaffM>> result = response.body();
                        if (result.isSuccess()) {
                             iView.lodeSuccess(result.getObj());
                        } else {
                            iView.lodeFailed();
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<ApiResult<List<DeliveryStaffM>>> response) {
                        super.onCacheSuccess(response);
                        onSuccess(response);
                    }

                    @Override
                    public void onError(Response<ApiResult<List<DeliveryStaffM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                    }
                });

    }



}
