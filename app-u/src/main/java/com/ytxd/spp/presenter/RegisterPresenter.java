package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.ytxd.spp.model.UserM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IRegisterView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class RegisterPresenter extends BasePresenter<IRegisterView> {

    public RegisterPresenter(Context context, IRegisterView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void sendSmscode(String phone) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>post(Apis.SendSmscode_P)//
                .params("Phone", phone)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        ApiResult result = response.body();
                        if (response.body().isSuccess()) {
                            iView.sendCodeSuccess((String) result.getObj());
                            ToastUtil.showToastShort(context, "发送验证码成功");
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        super.onError(response);
                        ToastUtil.showToastShort(context, "发送验证码失败");
                    }
                });

    }


    public void registerPhone(String phone, String pwd, String invitecode) {
        PostRequest postRequest = OkGo.<ApiResult<UserM>>post(Apis.PhoneRegister)//
                .params("Phone", phone)
                .params("LoginPwd", pwd);
        if (!AbStrUtil.isEmpty(invitecode)) {
            postRequest.params("InvitationCode", invitecode);
        }
        iView.showDialogs();
        postRequest.execute(new JsonCallback<ApiResult<UserM>>() {
            @Override
            public void onSuccess(Response<ApiResult<UserM>> response) {
                iView.dissmisDialogs();
                ApiResult result = response.body();
                if (response.body().isSuccess()) {
                    ToastUtil.showToastShort(context, "注册成功");
                    iView.finishRegister();
                } else {
                    ToastUtil.showToastShort(context, result.getMsg());
                }
            }

            @Override
            public void onError(Response<ApiResult<UserM>> response) {
                iView.dissmisDialogs();
                super.onError(response);
                ToastUtil.showToastShort(context, "注册失败");
            }
        });


    }
}
