package com.ytxd.spp.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.ytxd.spp.R;
import com.ytxd.spp.base.G;
import com.ytxd.spp.model.AppVersionM;
import com.ytxd.spp.util.AbWifiUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by XY on 2016/12/27.
 */

public class AppUpdateDialog extends BaseDialogFragment {


    AppVersionM updateDean;
    @BindView(R.id.v)
    View v;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.number_progress_bar)
    NumberProgressBar numberProgressBar;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_speed)
    TextView tvSpeed;
    @BindView(R.id.btn_retry)
    Button btnRetry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_app_update, container, false);
        ButterKnife.bind(this, view);
        updateDean = (AppVersionM) getArguments().getSerializable("data");
        initView(view);
        initData();
        setCancelable(false);
        return view;

    }

    private void initData() {
        btnRetry.setVisibility(View.GONE);
        numberProgressBar.setVisibility(View.VISIBLE);
        try {
            tvTitle.setText("正在下载" + updateDean.getSoftwareVersionAIName() + ".....");
            File file = new File(G.STORAGEPATH, updateDean.getSoftwareVersionAIName() + ".apk");
            if (!file.exists()) {
                file.createNewFile();
            }
            downloadFile(updateDean.getDownUrl(), file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void initView(View view) {

    }


    private void downloadFile(String url, final File file) {
        if (!AbWifiUtil.isConnectivity(activity)) {
            ToastUtil.showToastShort(activity, activity.getResources().getString(R.string.net_wrong));
            return;
        }
        OkGo.<File>get(url)
                .execute(new FileCallback(file.getParentFile().getPath(), file.getName()) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        LogUtils.e("onSuccess:file:" + file.getAbsolutePath());
                        try {
                            tvTitle.setText(updateDean.getSoftwareVersionAIName() + "下载完成");
                            CommonUtils.intallApk(file, activity);
                            dismiss();
                        } catch (Exception e) {
                            tvTitle.setText(updateDean.getSoftwareVersionAIName() + "下载失败");
                            ToastUtil.showToastShort(activity, "下载失败");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        tvCurrent.setText(progress.currentSize + "");
                        tvTotal.setText(progress.totalSize + "");
                        tvSpeed.setText(progress.speed + "");
                        float c=progress.currentSize*1f;
                        float t=progress.totalSize*1f;
                        float pf = c / t;
                        int p = (int) (pf*100);
                        numberProgressBar.setProgress(p);
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        btnRetry.setVisibility(View.VISIBLE);
                        numberProgressBar.setVisibility(View.GONE);
                        tvTitle.setText(updateDean.getSoftwareVersionAIName() + "下载失败");
                        ToastUtil.showToastShort(activity, "下载app失败");
                    }

                });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
    }

    @OnClick({R.id.v, R.id.btn_retry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.v:
//                dismiss();
                break;
            case R.id.btn_retry:
                initData();
                break;
        }
    }
}
