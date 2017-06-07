package com.ytxd.spp.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.activity.main.AddOrEditAddressActivity;
import com.ytxd.spp.ui.adapter.AddressManaLV;
import com.ytxd.spp.util.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManaActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.msv)
    MultiStateView msv;
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
        mAdapter = new AddressManaLV(new ArrayList<String>(), this);
        lv.setAdapter(mAdapter);

        refresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.addItems(CommonUtils.getSampleList(15), true);
                msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
        }, 2000);

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

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        startActivity(AddOrEditAddressActivity.class);
    }
}
