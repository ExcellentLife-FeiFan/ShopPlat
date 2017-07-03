package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IEnsureOrderView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class EnsureOrderPresenter extends BasePresenter<IEnsureOrderView> {

    public EnsureOrderPresenter(Context context, IEnsureOrderView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getADList() {
        OkGo.<ApiResult<List<AddressM>>>get(Apis.GetUserSHAddressList)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<List<AddressM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<AddressM>>> response) {
                        ApiResult<List<AddressM>> result = response.body();
                        try {
                            if (result.isSuccess()) {
                                AddressM addressM = null;
                                for (int i = 0; i < result.getObj().size(); i++) {
                                    if (result.getObj().get(i).isIsDefault()) {
                                        addressM = result.getObj().get(i);
                                        break;
                                    }

                                }
                                if (null == addressM) {
                                    addressM = result.getObj().get(0);
                                }
                                iView.lodeAddress(addressM);
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeAddress(null);
                    }

                    @Override
                    public void onError(Response<ApiResult<List<AddressM>>> response) {
                        iView.lodeAddress(null);
                        super.onError(response);
                    }
                });

    }

    public void ensureOrder(final OrderM order) {

        iView.showDialogs();
        OkGo.<ApiResult<Object>>get(Apis.GenerateWFKOrder)//
                .params("UserCode", App.user.getUserCode())
                .params("SupermarketCode", order.getSupermarketCode())
                .params("SHAddressCode", order.getSHAddressCode())
                .params("UserCouponCode", order.getUserCouponCode())
                .params("ManJianCode", order.getManJianCode())
                .params("GoodsInfo", order.getGoodsInfo())
                .params("SDTime", order.getSDTime())
                .params("Remarks", order.getRemarks())
                .params("PayType", order.getPayType())
                .params("YPrice", order.getYPrice())
                .params("SJPrice", order.getSJPrice())
                .params("PSPrice", order.getPSPrice())
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dismissDialogs();
                        try {
                            ApiResult<Object> result = response.body();
                            if (result.isSuccess()) {
                                order.setOrderCode((String) result.getObj());
                                iView.ensureOrderSuccess(order);
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.showToastShort(context, "订单提交失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        ToastUtil.showToastShort(context, "订单提交失败");
                        iView.dismissDialogs();
                        super.onError(response);
                    }
                });


    }

}
