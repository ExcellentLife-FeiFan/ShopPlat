package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMerchantGoodView;

import java.util.ArrayList;
import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MerchantGoodPresenter extends BasePresenter<IMerchantGoodView> {

    public MerchantGoodPresenter(Context context, IMerchantGoodView iView) {
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


    public void addOrderGood(GoodM good, MerchantM merchantM, List<OrderGoodM> orderGoods) {
        if (null != good.getGoods() && good.getGoods().size() > 0) {
            List<ShoppingCartM.Goods> gAs = new ArrayList<>();
            for (int i = 0; i < orderGoods.size(); i++) {
                for (int j = 0; j < good.getGoods().size(); j++) {
                    if (good.getGoods().get(j).getGoodsCode().equals(orderGoods.get(i).getGoodsCode())) {
                        ShoppingCartM.Goods g=new ShoppingCartM.Goods();
                        g.setCount(orderGoods.get(i).getBuyNumber());
                        g.setGoodM(good.getGoods().get(j));
                        gAs.add(g);
                    }

                }
            }
            for (int i = 0; i < gAs.size(); i++) {
                ShoppingCartUtil.addGoods(context, merchantM, gAs.get(i).getGoodM(), gAs.get(i).getCount());
                for (int j = 0; j < orderGoods.size(); j++) {
                    if (gAs.get(i).getGoodM().getGoodsCode().equals(orderGoods.get(j).getGoodsCode())) {
                        orderGoods.remove(j);
                        break;
                    }
                }

            }
        } else {
            int count = 0;
            boolean isCotained = false;
            GoodM gA = null;
            for (int i = 0; i < orderGoods.size(); i++) {
                if (good.getGoodsCode().equals(orderGoods.get(i).getGoodsCode())) {
                    count = orderGoods.get(i).getBuyNumber();
                    orderGoods.remove(i);
                    isCotained = true;
                    gA = good;
                    break;
                }
            }
            if (isCotained) {
                ShoppingCartUtil.addGoods(context, merchantM, gA, count);

            }
        }
    }
}