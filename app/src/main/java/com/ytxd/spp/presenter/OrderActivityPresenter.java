package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IOrderActivityView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class OrderActivityPresenter extends BasePresenter<IOrderActivityView> {

    public OrderActivityPresenter(Context context, IOrderActivityView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getOrderList(final int mode, int pageIndex) {
        OkGo.<ApiResult<List<OrderM>>>get(Apis.GetUserOrderList)//
                .params("UserCode", App.user.getUserCode())
                .params("PageIndex", pageIndex)
                .params("PageSize", "10")
                .execute(new JsonCallback<ApiResult<List<OrderM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<OrderM>>> response) {
                        try {
                            ApiResult<List<OrderM>> result = response.body();
                            if (result.isSuccess()) {
                                List<OrderM> datas = result.getObj();
                                if (mode == CommonUtils.LODEMORE) {
                                    if (null == datas || datas.size() == 0) {
                                        iView.lodeMoreFailed();
                                    } else {
                                        iView.lodeMoreSuccess(result.getObj());
                                    }
                                } else {
                                    if (null == datas || datas.size() == 0) {
                                        iView.refreshFailed();
                                    } else {
                                        iView.refreshSuccess(datas);
//                                        currentPage = 1;
//                                        mAdapter.setNewData(datas);
                                    }
                                }
                                return;
                            }
                            ToastUtil.showToastShort(context, result.getMsg());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (mode == CommonUtils.LODEMORE) {
                            iView.lodeMoreFailed();
                        } else {
                            iView.refreshFailed();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<List<OrderM>>> response) {
                        if (mode == CommonUtils.LODEMORE) {
                            iView.lodeMoreFailed();
                        } else {
                            iView.refreshFailed();
                        }
                        super.onError(response);
                    }
                });

    }

   /* private void loadData(List<OrderM2> items,int type) {
        Map<String, List<OrderM2>> maps = new LinkedHashMap<>();
        for (int i = 0; i < items.size(); i++) {
            if (maps.containsKey(items.get(i).getOrderCode())) {
                maps.get(items.get(i).getOrderCode()).add(items.get(i));
            } else {
                List<OrderM2> ors = new ArrayList<>();
                ors.add(items.get(i));
                maps.put(items.get(i).getOrderCode(), ors);
            }
        }
        List<OrderM> datas = new ArrayList<>();
        String[] keyss = new String[]{};
        String[] keys=maps.keySet().toArray(keyss);
        for (int i = 0; i < keys.length; i++) {
            List<OrderM2> ors = maps.get(keys[i]);
            OrderM order = ors.get(0);
            List<ShoppingCartM.Goods> goods = new ArrayList<>();
            for (int i1 = 0; i1 < ors.size(); i1++) {
                ShoppingCartM.Goods good = new ShoppingCartM.Goods();
                good.setCount(ors.get(i1).getBuyNumber());
                GoodM goodM = new GoodM();
                goodM.setGoodsTitle(ors.get(i1).getGoodsTitle());
                goodM.setGoodsCode(ors.get(i1).getGoodsCode());
                goodM.setContent(ors.get(i1).getContent());
                goodM.setGoodsTypeCode(ors.get(i1).getGoodsTypeCode());
                goodM.setLogoPaths(ors.get(i1).getLogoPaths());
                goodM.setYPrice(ors.get(i1).getYPrice());
                goodM.setXPrice(ors.get(i1).getXPrice());
                good.setGoodM(goodM);
                goods.add(good);
            }
//            order.setGoods(goods);
            datas.add(order);
        }
        if (type == CommonUtils.LODEMORE) {
            iView.lodeMoreSuccess(datas);
        }else{
            iView.refreshSuccess(datas);
        }
    }*/


}
