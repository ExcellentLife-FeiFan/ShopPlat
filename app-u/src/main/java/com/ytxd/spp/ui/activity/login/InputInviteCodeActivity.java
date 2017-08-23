package com.ytxd.spp.ui.activity.login;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.presenter.InputInviteCodePresenter;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.view.IInputInviiteCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InputInviteCodeActivity extends BaseActivity<InputInviteCodePresenter> implements View.OnClickListener, IInputInviiteCodeView {

    @BindView(R.id.et)
    EditText et;


    @Override
    protected void initPresenter() {
        presenter = new InputInviteCodePresenter(this, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_invite_code);
        getBar().initActionBar("输入邀请码", "", "跳过", -1, -1, this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        String invieCode = et.getText().toString();
        if (AbStrUtil.isEmpty(invieCode)) {
            showToast("请输入邀请码");
        } else {
            presenter.updateUserInviter(invieCode);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
                startActivity(MainActivity.class);
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void setInvitecodeSuccess() {
        startActivity(MainActivity.class);
        AppManager.getInstance().killActivity(this);
    }
}
