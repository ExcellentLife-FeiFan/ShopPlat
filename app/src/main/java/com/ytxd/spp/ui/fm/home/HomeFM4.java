package com.ytxd.spp.ui.fm.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.ui.activity.login.LoginActivity;
import com.ytxd.spp.ui.activity.mine.AddressManaActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM4 extends BaseFragment {

    Unbinder unbinder;

    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_4;
    }

    @Override
    public void initView() {

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


    @OnClick({R.id.rl_address_mana, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_address_mana:
                startActivity(AddressManaActivity.class);
                break;
            case R.id.btn_logout:
                startActivity(LoginActivity.class);
                AppManager.getInstance().killActivity(activity);
                break;
        }
    }
}
