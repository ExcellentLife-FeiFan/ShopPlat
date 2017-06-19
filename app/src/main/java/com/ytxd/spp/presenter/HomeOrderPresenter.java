package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IHomeOrderView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class HomeOrderPresenter extends BasePresenter<IHomeOrderView> {

    public HomeOrderPresenter(Context context, IHomeOrderView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getOrderList() {
        OkGo.<ApiResult<List<OrderM>>>get(Apis.GetUserOrderList)//
                .params("UserCode", App.user.getUserCode())
                .params("PageIndex", "1")
                .params("PageSize", "10")
                .execute(new JsonCallback<ApiResult<List<OrderM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<OrderM>>> response) {
                        try {
                            ApiResult<List<OrderM>> result = response.body();
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
                    public void onError(Response<ApiResult<List<OrderM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


}
