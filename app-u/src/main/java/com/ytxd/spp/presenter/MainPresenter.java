package com.ytxd.spp.presenter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.model.AppVersionM;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.ui.dialog.AppUpdateDialog;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMainView;

import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class MainPresenter extends BasePresenter<IMainView> {

    public MainPresenter(Context context, IMainView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getCouponList() {
        OkGo.<ApiResult<List<CouponM>>>get(Apis.GetUserCoupon)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<List<CouponM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<CouponM>>> response) {
                        try {
                            ApiResult<List<CouponM>> result = response.body();
                            if (result.isSuccess()) {
                                iView.lodeCouponSuccess(result.getObj());
                                return;
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        iView.lodeCouponFailed();
                    }

                    @Override
                    public void onError(Response<ApiResult<List<CouponM>>> response) {
                        iView.lodeCouponFailed();
                        super.onError(response);
                        LogUtils.e("");
                    }
                });

    }

    public void setCouponRead() {
        OkGo.<ApiResult<Object>>get(Apis.SetUserCouponAllRead)//
                .params("UserCode", App.user.getUserCode())
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        try {
                            ApiResult<Object> result = response.body();
                            if (result.isSuccess()) {
                            } else {
                                ToastUtil.showToastShort(context, result.getMsg());
                            }
                        } catch (Exception e) {
                            ToastUtil.showToastShort(context, "设置优惠券已读失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        ToastUtil.showToastShort(context, "设置优惠券已读失败");
                        super.onError(response);
                    }
                });

    }

    public void checkForUpdateApp(final AppCompatActivity context) {
        OkGo.<ApiResult<AppVersionM>>get(Apis.GetSoftwareNewsVersion)//
                .params("AndroidOrIos", "1")
                .execute(new JsonCallback<ApiResult<AppVersionM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<AppVersionM>> response) {
                        try {
                            final ApiResult<AppVersionM> result = response.body();
                            if (result.isSuccess()) {
                                if (null != result.getObj()) {
                                    PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                    int build = pi.versionCode;
                                    String versionName = pi.versionName;
                                    versionName = versionName + "." + build;
                                    if (!versionName.equals(result.getObj().getSoftwareVersionAINo())) {
                                        String ss = "";
                                        if (CommonUtils.getBoolean(result.getObj().getIsQZUpdate())) {
                                            ss = "点确定按钮下载更新(此版本必须更新)";
                                        } else {
                                            ss = "点确定按钮下载更新";
                                        }
                                        DialogUtils.showDialog(context, "提示", "已检测到新版本：" + result.getObj().getSoftwareVersionAIName() + "\n\n" + result.getObj().getUpdateContent() + "\n\n" + ss, new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                if (which.name().equals(DialogAction.POSITIVE.name())) {
                                                    AppUpdateDialog appUpdateDialog = new AppUpdateDialog();
                                                    Bundle data = new Bundle();
                                                    data.putSerializable("data", result.getObj());
                                                    appUpdateDialog.setArguments(data);
                                                    appUpdateDialog.show(context.getFragmentManager(), "AppUpdateDialog");
                                                } else {
                                                    if (result.getObj().getIsQZUpdate() == 1) {
                                                        AppManager.getInstance().AppExit(context);
                                                    }
                                                }
                                            }
                                        });
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<AppVersionM>> response) {
                        super.onError(response);
                    }
                });

    }

    private void downloadApp(String downUrl) {


    }


}
