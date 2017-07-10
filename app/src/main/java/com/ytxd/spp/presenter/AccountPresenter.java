package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IAccountView;

import java.io.File;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class AccountPresenter extends BasePresenter<IAccountView> {

    public AccountPresenter(Context context, IAccountView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
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
                .params("file",file)
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


}
