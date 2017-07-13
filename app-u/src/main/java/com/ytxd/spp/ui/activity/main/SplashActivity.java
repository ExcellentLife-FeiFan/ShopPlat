package com.ytxd.spp.ui.activity.main;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.SplashPresenter;
import com.ytxd.spp.util.PermissionsActivity;
import com.ytxd.spp.util.PermissionsChecker;
import com.ytxd.spp.view.ISplashView;

public class SplashActivity extends BaseActivity<SplashPresenter> implements ISplashView {

    // 所需的全部权限
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private static final int REQUEST_CODE = 0; // 请求码

    private PermissionsChecker mPermissionsChecker; // 权限检测器


    @Override
    protected void initPresenter() {
        presenter = new SplashPresenter(this, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPermissionsChecker = new PermissionsChecker(this);
            // 缺少权限时, 进入权限配置页面
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
                return;
            }
        }
        new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                presenter.isFirst();
            }
        }.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        } else {
            new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    presenter.isFirst();
                }
            }.sendEmptyMessageDelayed(1, 2000);
        }
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    public void init() {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void startToMain() {
        startActivity(MainActivity.class);
        AppManager.getInstance().killActivity(this);
    }

    @Override
    public void startToLogin() {
        startActivity(MainActivity.class);
        AppManager.getInstance().killActivity(this);
    }

    @Override
    public void startToGuide() {

    }

    @Override
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dismissDialogs() {
        dismissDialog();
    }
}
