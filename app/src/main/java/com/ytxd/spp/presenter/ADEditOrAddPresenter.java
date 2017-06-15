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
import com.ytxd.spp.view.IADEditOrAddView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class ADEditOrAddPresenter extends BasePresenter<IADEditOrAddView> {

    public ADEditOrAddPresenter(Context context, IADEditOrAddView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void addAD(AddressM addressM) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.AddSHAddress_P)//
                .params("UserCode", App.user.getUserCode())
                .params("Contacts", addressM.getContacts())
                .params("Phone", addressM.getPhone())
                .params("Sex", addressM.getSex())
                .params("PhoneCheck", addressM.isPhoneCheck()?"1":"0")
                .params("Lng", addressM.getLng())
                .params("Lat", addressM.getLat())
                .params("AddressTitle", addressM.getAddressTitle())
                .params("AddressDescribe", addressM.getAddressDescribe())
                .params("AddressContent", addressM.getAddressContent())
                .params("IsDefault", "0")
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.addSuccess();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }
    public void editAD(AddressM addressM) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.ModifySHAddress)//
                .params("SHAddressCode", addressM.getSHAddressCode())
                .params("UserCode", App.user.getUserCode())
                .params("Contacts", addressM.getContacts())
                .params("Phone", addressM.getPhone())
                .params("Sex", addressM.getSex())
                .params("PhoneCheck", addressM.isPhoneCheck()?"1":"0")
                .params("Lng", addressM.getLng())
                .params("Lat", addressM.getLat())
                .params("AddressTitle", addressM.getAddressTitle())
                .params("AddressDescribe", addressM.getAddressDescribe())
                .params("AddressContent", addressM.getAddressContent())
                .params("IsDefault", "0")
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.editSuccess();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
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
