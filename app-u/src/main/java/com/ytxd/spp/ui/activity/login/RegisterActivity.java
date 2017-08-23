package com.ytxd.spp.ui.activity.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.RegisterPresenter;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IRegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements View.OnClickListener, IRegisterView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_get_smscode)
    TextView tvGetSmscode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    String smsCode;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;

    @Override
    protected void initPresenter() {
        presenter = new RegisterPresenter(this, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        HideUtil.init(this);
        getBar().initActionBar("注册", this);
        time = new TimeCount(60000, 1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.tv_get_smscode, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_smscode:
                String phone1 = etPhone.getText().toString();
                if (AbStrUtil.isEmpty(phone1)) {
                    ToastUtil.showToastShort(this, "请输入手机号");
                } else {
                    presenter.sendSmscode(phone1);
                }
                break;
            case R.id.btn_register:
                String phone = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                String code = etSmscode.getText().toString();
                String invitecode = etInviteCode.getText().toString();
                if (AbStrUtil.isEmpty(phone)) {
                    ToastUtil.showToastShort(this, "请输入手机号");
                } /*else if (AbStrUtil.isEmpty(smsCode)) {
                    showToast("验证码未接收到?");
                } */else if (AbStrUtil.isEmpty(pwd)) {
                    ToastUtil.showToastShort(this, "请输入密码");
                }/* else if (!code.equals(this.smsCode)) {
                    showToast("验证码错误");
                } */else {
                    presenter.registerPhone(phone, pwd, invitecode);
                }
                break;
        }
    }

    private TimeCount time;

    @Override
    public void init() {

    }

    @Override
    public void sendCodeSuccess(String code) {
        time.start();
        this.smsCode = code;
    }

    @Override
    public void showDialogs() {
        showDialog();
    }


    @Override
    public void dissmisDialogs() {
        dismissDialog();
    }

    @Override
    public void startToMain() {
        startActivity(MainActivity.class);
        AppManager.getInstance().killActivity(this);
    }

    @Override
    public void stopTimer() {
        time.cancel();
    }

    @Override
    public void finishRegister(String phone, String pwd) {
        presenter.loginPhone(phone,pwd);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {//计时完毕时触发
            tvGetSmscode.setText("重新获取");
            tvGetSmscode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {//计时过程显示
            tvGetSmscode.setClickable(false);
            tvGetSmscode.setText(String.format("重新获取（%s）", millisUntilFinished / 1000));
        }
    }
}
