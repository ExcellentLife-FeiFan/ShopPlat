package com.ytxd.sppm.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.net.ApiResult;
import com.ytxd.sppm.net.Apis;
import com.ytxd.sppm.net.JsonCallback;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.ToastUtil;
import com.ytxd.sppm.view.IMineFMView;


/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MineFMPresenter extends BasePresenter<IMineFMView>{


    public MineFMPresenter(Context context, IMineFMView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void openClose(final boolean isOpen) {
        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.OpenClose)//
                .params("SupermarketCode", App.user.getSupermarketCode())
                .params("State", isOpen?"1":"0")
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.changeStateSuccess(isOpen);
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



}
