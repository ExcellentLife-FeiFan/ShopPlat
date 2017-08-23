package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMerchantView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MerchantPresenter extends BasePresenter<IMerchantView> {

    public MerchantPresenter(Context context, IMerchantView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

   /* public void getManjian(String supermarketCode) {
        OkGo.<ApiResult<List<MerchantM.ManJianBean>>>get(Apis.GetSupermarketManJianList)//
                .params("SupermarketCode", supermarketCode)
                .execute(new JsonCallback<ApiResult<List<MerchantM.ManJianBean>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<MerchantM.ManJianBean>>> response) {
                        try {
                            ApiResult<List<MerchantM.ManJianBean>> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeManjianSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<List<MerchantM.ManJianBean>>> response) {
                        super.onError(response);
                    }
                });

    }

    public void getMerState(String supermarketCode) {
        OkGo.<ApiResult<MerchantStateM>>get(Apis.GetSupermarketBusinessTimeIsOpen)//
                .params("SupermarketCode", supermarketCode)
                .execute(new JsonCallback<ApiResult<MerchantStateM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<MerchantStateM>> response) {
                        try {
                            ApiResult<MerchantStateM> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeStateSucess(result.getObj());
                            } else {
//                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<MerchantStateM>> response) {
                        super.onError(response);
                    }
                });

    }*/

    public void getMerchant(String supermarketCode, final boolean isLodeGood) {
        OkGo.<ApiResult<MerchantM>>get(Apis.GetSupermarketInfo)//
                .params("SupermarketCode", supermarketCode)
                .execute(new JsonCallback<ApiResult<MerchantM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<MerchantM>> response) {
                        try {
                            ApiResult<MerchantM> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeInfoSucess(result.getObj(),isLodeGood);
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeInfoFailure(isLodeGood);
                    }

                    @Override
                    public void onError(Response<ApiResult<MerchantM>> response) {
                        super.onError(response);
                        iView.lodeInfoFailure(isLodeGood);
                    }
                });

    }


}
