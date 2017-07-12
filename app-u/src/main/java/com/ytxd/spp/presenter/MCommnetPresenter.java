package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.MerchantEvaluateM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMCommentView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MCommnetPresenter extends BasePresenter<IMCommentView> {

    public MCommnetPresenter(Context context, IMCommentView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getList(String supermarketCode) {
        OkGo.<ApiResult<List<MerchantEvaluateM>>>get(Apis.GetSupermarketEvaluate)//
                .params("SupermarketCode", supermarketCode)
                .execute(new JsonCallback<ApiResult<List<MerchantEvaluateM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<MerchantEvaluateM>>> response) {
                        try {
                            ApiResult<List<MerchantEvaluateM>> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeFailed();
                    }

                    @Override
                    public void onError(Response<ApiResult<List<MerchantEvaluateM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                    }
                });

    }


}
