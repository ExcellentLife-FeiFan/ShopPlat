package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.GoodEvaluateM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IGCommentView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class GCommnetPresenter extends BasePresenter<IGCommentView> {

    public GCommnetPresenter(Context context, IGCommentView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getList(String goodsCode) {
        OkGo.<ApiResult<List<GoodEvaluateM>>>get(Apis.GetGoodsEvaluate)//
                .params("GoodsCode", goodsCode)
                .execute(new JsonCallback<ApiResult<List<GoodEvaluateM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<GoodEvaluateM>>> response) {
                        try {
                            ApiResult<List<GoodEvaluateM>> result = response.body();
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
                    public void onError(Response<ApiResult<List<GoodEvaluateM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                    }
                });

    }


}
