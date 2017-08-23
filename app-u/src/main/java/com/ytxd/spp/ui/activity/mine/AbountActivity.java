package com.ytxd.spp.ui.activity.mine;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.AbountPresenter;
import com.ytxd.spp.view.IAbountView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ytxd.spp.base.App.context;

public class AbountActivity extends BaseActivity<AbountPresenter> implements View.OnClickListener, IAbountView {

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected void initPresenter() {
        presenter = new AbountPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abount);
        ButterKnife.bind(this);
        getBar().initActionBar("关于", this);
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int build = pi.versionCode;
            String versionName = pi.versionName;
            tvVersion.setText("v" + versionName + "." + build);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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

    @OnClick(R.id.tv_check_update)
    public void onViewClicked() {
        presenter.checkForUpdateApp(this);
    }

    @Override
    public void init() {

    }

    @Override
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dissmissDialogs() {
        dismissDialog();
    }
}
