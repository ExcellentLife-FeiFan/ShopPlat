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
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.model.AppVersionM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.ui.dialog.AppUpdateDialog;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IAbountView;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class AbountPresenter extends BasePresenter<IAbountView> {

    public AbountPresenter(Context context, IAbountView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void checkForUpdateApp(final AppCompatActivity context) {
        iView.showDialogs();
        OkGo.<ApiResult<AppVersionM>>get(Apis.GetSoftwareNewsVersion)//
                .params("AndroidOrIos", "1")
                .execute(new JsonCallback<ApiResult<AppVersionM>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<AppVersionM>> response) {
                        iView.dissmissDialogs();
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
                                    } else {
                                        ToastUtil.showToastShort(context, "已是最新版本");
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<AppVersionM>> response) {
                        iView.dissmissDialogs();
                        super.onError(response);
                    }
                });

    }

}
