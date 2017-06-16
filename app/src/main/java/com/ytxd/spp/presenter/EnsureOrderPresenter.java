package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.AddressM;
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
                                    if(result.getObj().get(i).isIsDefault()){
                                        addressM = result.getObj().get(i);
                                        break;
                                    }

                                }
                                if(null==addressM){
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

}
