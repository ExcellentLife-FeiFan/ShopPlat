package com.ytxd.spp.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_loginout)
    LinearLayout llLoginout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getBar().initActionBar("设置", this);
        if (!CommonUtils.isLogined2()) {
            llLoginout.setVisibility(View.GONE);
        }
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
                if (CommonUtils.isLogined(activity)) {
                    startActivity(AccountActivity.class);
                }
                break;
            case R.id.rl_normal:
                break;
            case R.id.rl_abount:
                break;
            case R.id.tv_logout:
                App.user = null;
                SPUtil.getInstance().putString("pwd", "");
                AppManager.getInstance().killActivity(activity);
                AppManager.getInstance().killActivity(MainActivity.class);
                startActivity(MainActivity.class);
                break;
        }
    }
}
