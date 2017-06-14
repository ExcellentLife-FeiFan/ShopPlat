package com.ytxd.spp.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.RefreshADEvent;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.presenter.AddressManaPresenter;
import com.ytxd.spp.ui.activity.mine.account.AddOrEditAddressActivity;
import com.ytxd.spp.ui.adapter.AddressManaLV;
import com.ytxd.spp.view.IAddressManaView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddressManaActivity extends BaseActivity<AddressManaPresenter> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener,IAddressManaView {

    @BindView(R.id.msv)
    MultiStateView msv;
    AddressManaLV mAdapter;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;

    @Override
    protected void initPresenter() {
        presenter = new AddressManaPresenter(activity, this);
        presenter.init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_mana);
        ButterKnife.bind(this);
        getBar().initActionBar("地址管理", this);
        refresh.setOnRefreshListener(this);
        mAdapter = new AddressManaLV(new ArrayList<AddressM>(), this);
        lv.setAdapter(mAdapter);
        refresh.setRefreshing(true);
        onRefresh();
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
        presenter.getADList();
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked() {
        startActivity(AddOrEditAddressActivity.class);
    }

    @Override
    public void init() {

    }

    @Override
    public void loginSuccess(List<AddressM> items) {
        refresh.setRefreshing(false);
        mAdapter.addItems(items,true);
        msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void loginFailed() {
        refresh.setRefreshing(false);
        msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }


    public void onEvent(RefreshADEvent event) {
        msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        presenter.getADList();
    }
}
