package com.ytxd.spp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.UserM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.SPUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.ILoginView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class LoginPresenter extends BasePresenter<ILoginView> implements Handler.Callback {

    private Handler handler;
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    public LoginPresenter(Context context, ILoginView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
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
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<UserM>> response) {
                        iView.dismissDialogs();
                        super.onError(response);
                    }
                });

    }

    public void initHandler() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public void loginByOhters(Platform platform) {
        final String accessToken = platform.getDb().getToken(); // 获取授权token
        final String openId = platform.getDb().getUserId(); // 获取用户在此平台的ID
        iView.showDialogs();
        GetRequest getRequest = OkGo.<ApiResult<UserM>>get(Apis.ThirdPartyLogin)
                .params("Code", accessToken);
        String platformName = platform.getName();
        if (platformName.equals("QQ")) {
            getRequest.params("Type", "QQ");
        } else if (platformName.equals("Wechat")) {
            getRequest.params("Type", "WeChat");
        }
        getRequest.execute(new JsonCallback<ApiResult<UserM>>() {
            @Override
            public void onSuccess(Response<ApiResult<UserM>> response) {
                iView.dismissDialogs();
                ApiResult<UserM> result = response.body();
                if (result.isSuccess()) {
                    iView.loginOtherSuccess(result.getObj());
                } else {
                    ToastUtil.showToastShort(context, result.getMsg());
                }
            }

            @Override
            public void onError(Response<ApiResult<UserM>> response) {
                iView.dismissDialogs();
                super.onError(response);
            }
        });

    }

    public void loginQQ() {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(paListener);
        plat.showUser(null);
    }

    public void loginWechat() {
        Platform plat = ShareSDK.getPlatform(Wechat.NAME);
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(paListener);
        plat.showUser(null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_AUTH_CANCEL: {
                // 取消
                ToastUtil.showToastShort(context, "取消");
            }
            break;
            case MSG_AUTH_ERROR: {
                // 失败
                ToastUtil.showToastShort(context, "授权失败");
            }
            break;
            case MSG_AUTH_COMPLETE: {
                Object[] obj = (Object[]) msg.obj;
                loginByOhters((Platform) obj[0]);
            }
            break;
        }
        return false;
    }

    PlatformActionListener paListener = new PlatformActionListener() {
        @Override
        public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
            if (action == Platform.ACTION_USER_INFOR) {
                Message msg = new Message();
                msg.what = MSG_AUTH_COMPLETE;
                msg.arg2 = action;
                msg.obj = new Object[]{plat, res};
                handler.sendMessage(msg);
            }
        }

        public void onError(Platform plat, int action, Throwable t) {
            if (action == Platform.ACTION_USER_INFOR) {
                Message msg = new Message();
                msg.what = MSG_AUTH_ERROR;
                msg.arg2 = action;
                msg.obj = t;
                handler.sendMessage(msg);
            }
            t.printStackTrace();
        }

        public void onCancel(Platform plat, int action) {
            if (action == Platform.ACTION_USER_INFOR) {
                Message msg = new Message();
                msg.what = MSG_AUTH_CANCEL;
                msg.arg2 = action;
                msg.obj = plat;
                handler.sendMessage(msg);
            }
        }
    };


}
