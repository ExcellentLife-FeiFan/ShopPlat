package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.UserM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.SPUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.ISplashView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class SplashPresenter extends BasePresenter<ISplashView> {

    public SplashPresenter(Context context, ISplashView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void isFirst() {
        boolean isFirst = SPUtil.getInstance().getBoolean("isFirst", true);
        if (isFirst) {
            SPUtil.getInstance().putBoolean("isFirst", false);
            iView.startToMain();
        } else {
            String phone = SPUtil.getInstance().getString("phone");
            String pwd = SPUtil.getInstance().getString("pwd");
            if (!AbStrUtil.isEmpty(phone) && !AbStrUtil.isEmpty(pwd)) {
                loginPhone(phone, pwd);
            } else {
                iView.startToLogin();
            }
        }
    }

    public void loginPhone(final String phone, final String pwd) {
        iView.showDialogs();
        OkGo.<ApiResult<UserM>>get(Apis.PhoneLogin)//
                .params("Phone", phone)
                .params("LoginPwd", pwd)
                .execute(new JsonCallback<ApiResult<UserM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<UserM>> response) {
                        iView.dismissDialogs();
                        ApiResult<UserM> result = response.body();
                        if (result.isSuccess()) {
                            App.user = result.getObj();
                            SPUtil.getInstance().putString("phone", phone);
                            SPUtil.getInstance().putString("pwd", pwd);
                            iView.startToMain();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                            iView.startToLogin();
                        }
                        LogUtils.e("");
                    }

                    @Override
                    public void onError(Response<ApiResult<UserM>> response) {
                        iView.dismissDialogs();
                        iView.startToLogin();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }


}
