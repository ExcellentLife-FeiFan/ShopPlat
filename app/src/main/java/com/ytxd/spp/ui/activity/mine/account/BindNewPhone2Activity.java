package com.ytxd.spp.ui.activity.mine.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.util.HideUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BindNewPhone2Activity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_smscode)
    EditText etSmscode;
    @BindView(R.id.tv_get_smscode)
    TextView tvGetSmscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_new_phone_2);
        ButterKnife.bind(this);
        HideUtil.init(this);
        getBar().initActionBar("绑定新手机号", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.tv_get_smscode, R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_smscode:
                break;
            case R.id.btn_finish:
                AppManager.getInstance().killActivity(this);
                AppManager.getInstance().killActivity(BindNewPhone1Activity.class);
                break;
        }
    }
}
