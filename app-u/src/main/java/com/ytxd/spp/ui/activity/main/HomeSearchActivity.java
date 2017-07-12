package com.ytxd.spp.ui.activity.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeSearchActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
            case R.id.rl_search:
                break;
        }
    }
}
