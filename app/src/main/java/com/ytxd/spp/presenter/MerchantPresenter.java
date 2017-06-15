package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMerchantView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MerchantPresenter extends BasePresenter<IMerchantView> {

    public MerchantPresenter(Context context, IMerchantView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getGoodList(String supermarketCode) {
        OkGo.<ApiResult<List<CatagaryM>>>get(Apis.GetGoodsBySupermarket)//
                .params("SupermarketCode", supermarketCode)
                .execute(new JsonCallback<ApiResult<List<CatagaryM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<CatagaryM>>> response) {
                        try {
                            ApiResult<List<CatagaryM>> result = response.body();
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
                    public void onError(Response<ApiResult<List<CatagaryM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


}
