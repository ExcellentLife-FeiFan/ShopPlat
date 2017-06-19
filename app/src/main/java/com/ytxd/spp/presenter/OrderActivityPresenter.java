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
                                        iView.lodeMoreSuccess(datas);
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


}
