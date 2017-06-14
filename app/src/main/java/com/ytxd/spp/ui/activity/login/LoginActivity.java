package com.ytxd.spp.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.LoginPresenter;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.SPUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView {

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
        String phone = SPUtil.getInstance().getString("phone");
        if(!AbStrUtil.isEmpty(phone)){
            etPhone.setText(phone);
        }
        String pwd = SPUtil.getInstance().getString("pwd");
        if(!AbStrUtil.isEmpty(pwd)){
            etPwd.setText(pwd);
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this, this);
        presenter.init();
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
                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                if (AbStrUtil.isEmpty(phone)) {
                    ToastUtil.showToastShort(this, "请输入手机号");
                } else if (AbStrUtil.isEmpty(pwd)) {
                    ToastUtil.showToastShort(this, "请输入密码");
                } else {
                    presenter.loginPhone(phone, pwd);
                }
                break;
            case R.id.ll_wechat:
                break;
            case R.id.ll_qq:
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void loginSuccess() {
        startActivity(MainActivity.class);
    }

    @Override
    public void startToMain() {
        startActivity(MainActivity.class);
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
