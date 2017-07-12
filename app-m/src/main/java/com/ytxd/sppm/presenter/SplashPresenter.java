package com.ytxd.sppm.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.model.UserM;
import com.ytxd.sppm.net.ApiResult;
import com.ytxd.sppm.net.Apis;
import com.ytxd.sppm.net.JsonCallback;
import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.LogUtils;
import com.ytxd.sppm.util.SPUtil;
import com.ytxd.sppm.util.ToastUtil;
import com.ytxd.sppm.view.ISplashView;


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
            iView.startToLogin();
        } else {
            String name = SPUtil.getInstance().getString("name");
            String pwd = SPUtil.getInstance().getString("pwd");
            if (!AbStrUtil.isEmpty(name) && !AbStrUtil.isEmpty(pwd)) {
                loginPhone(name, pwd);
            } else {
                iView.startToLogin();
            }
        }
    }

    public void loginPhone(final String name, final String pwd) {
        iView.showDialogs();
        OkGo.<ApiResult<UserM>>get(Apis.Login)//
                .params("LoginName", name)
                .params("LoginPwd", pwd)
                .execute(new JsonCallback<ApiResult<UserM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<UserM>> response) {
                        iView.dismissDialogs();
                        ApiResult<UserM> result = response.body();
                        if (result.isSuccess()) {
                            App.user = result.getObj();
                            SPUtil.getInstance().putString("name", name);
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
