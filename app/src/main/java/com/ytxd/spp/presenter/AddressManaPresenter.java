package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IAddressManaView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class AddressManaPresenter extends BasePresenter<IAddressManaView> {

    public AddressManaPresenter(Context context, IAddressManaView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getADList() {
        OkGo.<ApiResult<List<AddressM>>>get(Apis.GetUserSHAddressList)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<List<AddressM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<AddressM>>> response) {
                        ApiResult<List<AddressM>> result = response.body();
                        if (result.isSuccess()) {
                            iView.loginSuccess(result.getObj());
                        } else {
                            iView.loginFailed();
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<List<AddressM>>> response) {
                        iView.loginFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


}
