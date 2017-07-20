package com.ytxd.spp.ui.dialog;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by XY on 2016/12/27.
 */

public class InvitecodeShowDialog extends BaseDialogFragment {

    @BindView(R.id.tv_invitecode)
    TextView tvInvitecode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_show_invitecode, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;

    }

    private void initData() {


    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void initView() {
        CommonUtils.setText(tvInvitecode, App.user.getInvitationCode());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.v, R.id.btn_copy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v:
                dismiss();
                break;
            case R.id.btn_copy:
                if(!AbStrUtil.isEmpty(App.user.getInvitationCode())){
                    ClipboardManager cm = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                    // 将文本内容放到系统剪贴板里。
                    cm.setText(App.user.getInvitationCode());
                    ToastUtil.showToastShort(activity, "邀请码已复制到粘贴板!");
                }else{
                    ToastUtil.showToastShort(activity, "邀请码有误!");
                }
                break;

        }
    }
}
