package com.ytxd.spp.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.util.HideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        HideUtil.init(this);
    }

    @OnClick({R.id.tv_right, R.id.tv_findpwd, R.id.btn_login, R.id.ll_wechat, R.id.ll_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                startActivity(RegisterActivity.class);
                break;
            case R.id.tv_findpwd:
                startActivity(FindPwdActivity.class);
                break;
            case R.id.btn_login:
                startActivity(MainActivity.class);
                break;
            case R.id.ll_wechat:
                break;
            case R.id.ll_qq:
                break;
        }
    }
}
