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
import com.ytxd.spp.view.IInputInviiteCodeView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class InputInviteCodePresenter extends BasePresenter<IInputInviiteCodeView> {

    public InputInviteCodePresenter(Context context, IInputInviiteCodeView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void updateUserInviter(String invitationCode) {
        OkGo.<ApiResult<Object>>get(Apis.UpdateUserInviter)//
                .params("UserCode", App.user.getUserCode())
                .params("InvitationCode", invitationCode)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                           iView.setInvitecodeSuccess();
                        } else {
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        super.onError(response);
                        ToastUtil.showToastShort(context, CommonUtils.getString(R.string.action_failure));
                    }
                });


    }

}
