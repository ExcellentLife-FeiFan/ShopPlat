package com.ytxd.spp.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.RefreshADEvent;
import com.ytxd.spp.event.SelectAddressEvent;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.presenter.AddressManaPresenter;
import com.ytxd.spp.ui.activity.mine.account.AddOrEditAddressActivity;
import com.ytxd.spp.ui.adapter.AddressManaLV;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.view.IAddressManaView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddressManaActivity extends BaseActivity<AddressManaPresenter> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, IAddressManaView {

    @BindView(R.id.msv)
    MultiStateView msv;
    AddressManaLV mAdapter;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    String selectAddress;

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
        selectAddress=getIntent().getStringExtra("selectAddress");
        getBar().initActionBar("地址管理", this);
        refresh.setOnRefreshListener(this);
        mAdapter = new AddressManaLV(new ArrayList<AddressM>(), this);
        mAdapter.setPresenter(presenter);
        lv.setAdapter(mAdapter);
        if(!AbStrUtil.isEmpty(selectAddress)){
            getBar().initActionBar("选择地址", this);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EventBus.getDefault().post(new SelectAddressEvent(mAdapter.getItem(position)));
                    AppManager.getInstance().killActivity(activity);
                }
            });
        }
//        refresh.setRefreshing(true);
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
    public void lodeSuccess(List<AddressM> items) {
        refresh.setRefreshing(false);
        mAdapter.addItems(items, true);
        if (mAdapter.getCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        if(!AbStrUtil.isEmpty(selectAddress)){
            if(mAdapter.getCount()==0){
                EventBus.getDefault().post(new SelectAddressEvent(null));
            }
        }
    }

    @Override
    public void lodeFailed() {
        refresh.setRefreshing(false);
        msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    @Override
    public void deleteSuccess() {
        refresh.setRefreshing(true);
        onRefresh();
    }


    public void onEvent(RefreshADEvent event) {
        msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        presenter.getADList();
    }
}
