package com.ytxd.spp.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.UserM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IAccountView;

import java.io.File;
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
public class AccountPresenter extends BasePresenter<IAccountView> implements Handler.Callback {

    public AccountPresenter(Context context, IAccountView iView) {
        super(context, iView);
    }

    private Handler handler;
    private static final int MSG_AUTH_CANCEL = 1;
    private static final int MSG_AUTH_ERROR = 2;
    private static final int MSG_AUTH_COMPLETE = 3;

    @Override
    public void release() {
    }

    public void initHandler() {
        handler = new Handler(Looper.getMainLooper(), this);
    }

    public void modifyIcon(final String path) {
        OkGo.<ApiResult<Object>>get(Apis.ModifyIcon)//
                .params("TitlePath", path)
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.changeIconSuccess();
                            App.user.setTitlePath(path);
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                        iView.dismissDialogs();
                        super.onError(response);
                    }
                });


    }

    public void modifyNickName(String nickname) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.ModifyNickName)//
                .params("NickName", nickname)
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.changeNicknameSuccess();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                        super.onError(response);
                    }
                });

    }

    public void upLoadImg(File file) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>post(Apis.UpFiles)//
                .params("file", file)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            modifyIcon((String) result.getObj());
                        } else {
                            iView.dismissDialogs();
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                        super.onError(response);
                    }
                });

    }

    public void bindThirdPartyCode(Platform platform) {
        iView.showDialogs();
        final String openId = platform.getDb().getUserId(); // 获取用户在此平台的ID
        iView.showDialogs();
        GetRequest getRequest = OkGo.<ApiResult<UserM>>get(Apis.BindThirdPartyCode)
                .params("Code", openId)
                .params("UserCode", App.user.getUserCode());
        final String platformName = platform.getName();
        String type = "";
        if (platformName.equals("QQ")) {
            getRequest.params("Type", "QQ");
            type = "QQ";
        } else if (platformName.equals("Wechat")) {
            getRequest.params("Type", "WeChat");
            type = "WeChat";
        }
        final String finalType = type;
        getRequest.execute(new JsonCallback<ApiResult<Object>>() {
            @Override
            public void onSuccess(Response<ApiResult<Object>> response) {
                iView.dismissDialogs();
                try {
                    ApiResult<Object> result = response.body();
                    if (result.isSuccess()) {
                        iView.bindThirdPartSuccess(finalType, openId);
                    } else {
                        ToastUtil.showToastShort(context, result.getMsg());
                    }
                } catch (Exception e) {
                    ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Response<ApiResult<Object>> response) {
                iView.dismissDialogs();
                ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                super.onError(response);
            }
        });

    }

    public void bindQQ() {
        Platform plat = ShareSDK.getPlatform(QQ.NAME);
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
        }
        plat.setPlatformActionListener(paListener);
        plat.showUser(null);
    }

    public void bindWechat() {
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
                bindThirdPartyCode((Platform) obj[0]);
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
