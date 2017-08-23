package com.ytxd.spp.ui.activity.mine.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.BindPhone1Presenter;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IBindPhone1View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindNewPhone1Activity extends BaseActivity<BindPhone1Presenter> implements View.OnClickListener, IBindPhone1View {


    @BindView(R.id.tv_curphone)
    TextView tvCurphone;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_get_smscode)
    TextView tvGetSmscode;
    private String code;

    @Override
    protected void initPresenter() {
        presenter = new BindPhone1Presenter(this, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_new_phone_1);
        ButterKnife.bind(this);
        HideUtil.init(this);
        getBar().initActionBar("绑定新手机号", this);
        CommonUtils.setText(tvCurphone, App.user.getPhone());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.tv_get_smscode, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_smscode:
                String phone1 = App.user.getPhone();
                if (AbStrUtil.isEmpty(phone1)) {
                    ToastUtil.showToastShort(this, "手机号错误");
                } else {
                    presenter.sendSmscode(phone1);
                }
                break;
            case R.id.btn_next:
                String code = etSmscode.getText().toString();
                if (AbStrUtil.isEmpty(this.code)) {
                    showToast("验证码未接收到?");
                } else if (AbStrUtil.isEmpty(code)) {
                    ToastUtil.showToastShort(this, "请输入验证码");
                } else if (!code.equals(this.code)) {
                    showToast("验证码错误");
                } else {
                    startActivity(BindNewPhone2Activity.class);
                }
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void stopTimer() {

    }

    @Override
    public void sendCodeSuccess(String code) {
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
}
