package com.ytxd.sppm.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.sppm.R;
import com.ytxd.sppm.base.BaseActivity;
import com.ytxd.sppm.presenter.LoginPresenter;
import com.ytxd.sppm.ui.activity.main.MainActivity;
import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.HideUtil;
import com.ytxd.sppm.util.ToastUtil;
import com.ytxd.sppm.view.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView{

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;


    @Override
    protected void initPresenter() {
        presenter = new LoginPresenter(this, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HideUtil.init(this);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String name = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                if (AbStrUtil.isEmpty(name)) {
                    ToastUtil.showToastShort(this, "请输入登录名");
                } else if (AbStrUtil.isEmpty(pwd)) {
                    ToastUtil.showToastShort(this, "请输入密码");
                } else {
                    presenter.login(name, pwd);
                }
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
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dismissDialogs() {
      dismissDialog();
    }
}
