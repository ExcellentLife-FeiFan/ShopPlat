package com.ytxd.sppm.ui.fm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.base.AppManager;
import com.ytxd.sppm.base.BaseFragment;
import com.ytxd.sppm.presenter.MineFMPresenter;
import com.ytxd.sppm.ui.activity.login.LoginActivity;
import com.ytxd.sppm.ui.activity.main.BlueToothPrintActivity;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.ImageLoadUtil;
import com.ytxd.sppm.util.JpushUtil;
import com.ytxd.sppm.util.SPUtil;
import com.ytxd.sppm.view.IMineFMView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM2 extends BaseFragment<MineFMPresenter> implements IMineFMView {
    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_state)
    TextView tvState;
    private Unbinder unbinder;


    @Override
    protected void initPresenter() {
        presenter = new MineFMPresenter(activity, this);
        presenter.init();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_2;
    }

    @Override
    public void initView() {
        ImageLoadUtil.setImageNP(App.user.getLogoUrl(), civ, activity);
        CommonUtils.setText(tvName, App.user.getName());
        if (CommonUtils.getBoolean(App.user.getShopsOpen())) {
            CommonUtils.setText(tvState, "正在营业(切换)");
            tvState.setCompoundDrawables(CommonUtils.getTextDrawable(R.drawable.ic_open),null,null,null);
        } else {
            CommonUtils.setText(tvState, "暂停营业(切换)");
            tvState.setCompoundDrawables(CommonUtils.getTextDrawable(R.drawable.ic_close),null,null,null);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_poi_qr, R.id.ll_state, R.id.rl_print, R.id.rl_abount, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_poi_qr:
                break;
            case R.id.ll_state:
                if (CommonUtils.getBoolean(App.user.getShopsOpen())) {
                    presenter.openClose(false);
                } else {
                    presenter.openClose(true);
                }
                break;
            case R.id.rl_print:
                startActivity(BlueToothPrintActivity.class);
                break;
            case R.id.rl_abount:
                break;
            case R.id.tv_logout:
                JpushUtil.getInstance().setAliasNull(App.user.getSupermarketCode());
                SPUtil.getInstance().putString("pwd", "");
                startActivity(LoginActivity.class);
                AppManager.getInstance().killActivity(activity);
                App.user = null;
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void changeStateSuccess(boolean isOpen) {
        showToast("切换成功");
        if (isOpen) {
            App.user.setShopsOpen(1);
            CommonUtils.setText(tvState, "正在营业(切换)");
            tvState.setCompoundDrawables(CommonUtils.getTextDrawable(R.drawable.ic_open),null,null,null);
        } else {
            App.user.setShopsOpen(0);
            CommonUtils.setText(tvState, "暂停营业(切换)");
            tvState.setCompoundDrawables(CommonUtils.getTextDrawable(R.drawable.ic_close),null,null,null);
        }

    }

    @Override
    public void showDialogs() {
        CommonUtils.showDialog(activity);
    }

    @Override
    public void dismissDialogs() {
        CommonUtils.hideDialog();
    }
}