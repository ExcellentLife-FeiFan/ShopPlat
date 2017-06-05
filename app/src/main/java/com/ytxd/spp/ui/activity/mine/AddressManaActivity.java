package com.ytxd.spp.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.adapter.AddressManaLV;
import com.ytxd.spp.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddressManaActivity extends BaseActivity implements View.OnClickListener,SwipeRefreshLayout.OnRefreshListener {

    AddressManaLV mAdapter;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_mana);
        ButterKnife.bind(this);
        getBar().initActionBar("地址管理", this);
        refresh.setOnRefreshListener(this);
        mAdapter = new AddressManaLV(CommonUtils.getSampleList(9), this);
        lv.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @Override
    public void onRefresh() {
        lv.postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(false);
            }
        }, 2000);
    }
}
