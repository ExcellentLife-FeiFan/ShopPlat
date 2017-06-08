package com.ytxd.spp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.activity.login.LoginActivity;
import com.ytxd.spp.ui.activity.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getBar().initActionBar("设置", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }


    @OnClick({R.id.rl_account, R.id.rl_normal, R.id.rl_abount, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_account:
                startActivity(AccountActivity.class);
                break;
            case R.id.rl_normal:
                break;
            case R.id.rl_abount:
                break;
            case R.id.tv_logout:
                startActivity(LoginActivity.class);
                AppManager.getInstance().killActivity(activity);
                AppManager.getInstance().killActivity(MainActivity.class);
                break;
        }
    }
}
