package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.view.ILoginView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class LoginPresenter extends BasePresenter<ILoginView> {

    public LoginPresenter(Context context, ILoginView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void loginPhone(String phone, String pwd) {
        OkGo.<ApiResult<Object>>get(Apis.PhoneLogin)//
                .params("Phone", phone)
                .params("LoginPwd", pwd)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        if(response.body().isSuccess()){

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
