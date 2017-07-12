package com.ytxd.spp.ui.activity.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.ChangePwdPresenter;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.SPUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IChangePwdView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePwdActivity extends BaseActivity<ChangePwdPresenter> implements View.OnClickListener, IChangePwdView {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_get_smscode)
    TextView tvGetSmscode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    String type;
    @BindView(R.id.btn_register)
    Button btnRegister;
    String code, pwd;

    @Override
    protected void initPresenter() {
        presenter = new ChangePwdPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);
        ButterKnife.bind(this);
        HideUtil.init(this);
        time = new TimeCount(60000, 1000);
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            getBar().initActionBar("忘记密码", this);
            btnRegister.setText("找回密码");
        } else if (type.equals("2")) {
            getBar().initActionBar("修改密码", this);
            btnRegister.setText("立即修改");
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
                if (AbStrUtil.isEmpty(phone)) {
                    showToast("请输入手机号");
                } else if (AbStrUtil.isEmpty(code)) {
                    showToast("验证码未接收到?");
                } else if (AbStrUtil.isEmpty(pwd)) {
                    showToast("请输入密码");
                } else if (!code.equals(this.code)) {
                    showToast("验证码错误");
                } else {
                    this.pwd = pwd;
                    presenter.changePwd(phone, pwd);
                }
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void stopTimer() {
        time.cancel();
    }

    @Override
    public void finishChange() {
        if (type.equals("1")) {
            showToast("找回密码成功");
        } else if (type.equals("2")) {
            showToast("修改密码成功");
            App.user.setLoginPwd(pwd);
            SPUtil.getInstance().putString("pwd", pwd);
        }
        AppManager.getInstance().killActivity(this);

    }

    @Override
    public void sendCodeSuccess(String code) {
        time.start();
        this.code = code;
    }

    @Override
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dissmisDialogs() {
        dismissDialog();
    }


    private TimeCount time;

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
