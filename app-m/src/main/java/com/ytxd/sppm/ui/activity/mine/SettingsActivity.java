package com.ytxd.sppm.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.base.AppManager;
import com.ytxd.sppm.base.BaseActivity;
import com.ytxd.sppm.ui.activity.login.LoginActivity;
import com.ytxd.sppm.ui.activity.main.MainActivity;
import com.ytxd.sppm.util.SPUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rl_account)
    RelativeLayout rlAccount;
    @BindView(R.id.rl_normal)
    RelativeLayout rlNormal;
    @BindView(R.id.rl_abount)
    RelativeLayout rlAbount;
    @BindView(R.id.tv_logout)
    TextView tvLogout;

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
                break;
            case R.id.rl_normal:
                break;
            case R.id.rl_abount:
                break;
            case R.id.tv_logout:
                App.user = null;
                SPUtil.getInstance().putString("pwd", "");
                startActivity(LoginActivity.class);
                AppManager.getInstance().killActivity(activity);
                AppManager.getInstance().killActivity(MainActivity.class);
                break;
        }
    }
}
