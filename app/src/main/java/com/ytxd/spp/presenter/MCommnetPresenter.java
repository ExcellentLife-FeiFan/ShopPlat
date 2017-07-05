package com.ytxd.spp.presenter;

import android.content.Context;

import com.ytxd.spp.view.IMCommentView;

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

    public void getrList(final int mode, int pageIndex) {
        /*OkGo.<ApiResult<List<OrderM>>>get(Apis.GetUserOrderList)//
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
                });*/
        iView.lodeMoreSuccess(null);

    }


}
