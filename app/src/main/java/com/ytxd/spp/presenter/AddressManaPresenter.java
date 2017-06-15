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
                            iView.lodeSuccess(result.getObj());
                        } else {
                            iView.lodeFailed();
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<List<AddressM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


    public void deleteAD(String shAddressCode) {
        OkGo.<ApiResult<Object>>get(Apis.DeleteSHAddress)//
                .params("SHAddressCode", shAddressCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            ToastUtil.showToastShort(context, "设置成功");
                            iView.deleteSuccess();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        super.onError(response);
                        LogUtils.e("");
                    }
                });


    }

    public void setDefault(String shAddressCode) {
        OkGo.<ApiResult<Object>>get(Apis.SetDefaultSHAddress)//
                .params("UserCode", App.user.getUserCode())
                .params("SHAddressCode", shAddressCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.deleteSuccess();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        super.onError(response);
                        LogUtils.e("");
                    }
                });


    }
}
