package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMerchantView;

import java.util.List;

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

    public void getGoodList(String supermarketCode) {
        OkGo.<ApiResult<List<CatagaryM>>>get(Apis.GetGoodsBySupermarket)//
                .params("SupermarketCode", supermarketCode)
                .execute(new JsonCallback<ApiResult<List<CatagaryM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<CatagaryM>>> response) {
                        try {
                            ApiResult<List<CatagaryM>> result = response.body();
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
                    public void onError(Response<ApiResult<List<CatagaryM>>> response) {
                        iView.lodeFailed();
                        super.onError(response);
                    }
                });

    }
    public void getManjian(String supermarketCode) {
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

    public void getGoodFromOrder(String orderCode) {
        OkGo.<ApiResult<List<OrderGoodM>>>get(Apis.GetOrderGoodsInfo)
                .params("OrderCode", orderCode)
                .execute(new JsonCallback<ApiResult<List<OrderGoodM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<OrderGoodM>>> response) {
                        try {
                            ApiResult<List<OrderGoodM>> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeOrderGoodsSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeOrderGoodsFail();
                    }

                    @Override
                    public void onError(Response<ApiResult<List<OrderGoodM>>> response) {
                        iView.lodeOrderGoodsFail();
                        super.onError(response);
                    }
                });

    }



}
