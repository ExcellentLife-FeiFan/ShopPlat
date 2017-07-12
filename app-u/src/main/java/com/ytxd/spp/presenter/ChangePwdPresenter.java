package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.R;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IChangePwdView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class ChangePwdPresenter extends BasePresenter<IChangePwdView> {

    public ChangePwdPresenter(Context context, IChangePwdView iView) {
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


    public void changePwd(String phone, String pwd) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.ModifyPwdByPhone)//
                .params("Phone", phone)
                .params("NewPwd", pwd)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        ApiResult result = response.body();
                        if (response.body().isSuccess()) {
                            iView.finishChange();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dissmisDialogs();
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }
}
