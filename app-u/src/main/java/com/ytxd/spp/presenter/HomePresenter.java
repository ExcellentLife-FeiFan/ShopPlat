package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IHomeView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class HomePresenter extends BasePresenter<IHomeView> {

    public HomePresenter(Context context, IHomeView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getSPMList(String cityName) {
        OkGo.<ApiResult<List<MerchantM>>>get(Apis.GetSupermarkeyList)//
                .params("CityName", cityName)
                .execute(new JsonCallback<ApiResult<List<MerchantM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<MerchantM>>> response) {
                        try {
                            ApiResult<List<MerchantM>> result = response.body();
                            if (result.isSuccess()) {
                                iView.loginSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.loginFailed();
                    }

                    @Override
                    public void onError(Response<ApiResult<List<MerchantM>>> response) {
                        iView.loginFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


}
