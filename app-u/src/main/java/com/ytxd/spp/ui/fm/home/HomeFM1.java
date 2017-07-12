package com.ytxd.spp.ui.fm.home;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.event.AMapLocationUpdateEvent;
import com.ytxd.spp.event.HomeAddressChangeEvent;
import com.ytxd.spp.model.HomeAddressM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.presenter.HomePresenter;
import com.ytxd.spp.ui.activity.main.HomeSearchActivity;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.activity.main.SelectACEAddressActivity;
import com.ytxd.spp.ui.adapter.HomeMerchantA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.AMapLocationUtil;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.view.IHomeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM1 extends BaseFragment<HomePresenter> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IHomeView {

    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    Unbinder unbinder;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et)
    TextView et;
    HomeAddressM addressM = null;
    HomeMerchantA mAdapter;

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(activity, this);
        presenter.init();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_1;
    }

    @Override
    public void initView() {
        AMapLocationUtil.getInstance().startLocation();
        refreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(activity, R.color.line_gray, R.dimen.common_divider_height));
        mAdapter = new HomeMerchantA(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(MerchantDetailActivity.class, "data", mAdapter.getItem(i));
            }
        });
        msv.getView(MultiStateView.VIEW_STATE_EMPTY)
                .findViewById(R.id.tv_try_o).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SelectACEAddressActivity.class);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll_address, R.id.rl_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                startActivity(SelectACEAddressActivity.class);
                break;
            case R.id.rl_search:
                startActivity(HomeSearchActivity.class);
                break;
        }
    }

    public void onEvent(HomeAddressChangeEvent event) {
        if (null != event.getAddressM()) {
            addressM = event.getAddressM();
            tvAddress.setText(event.getAddressM().getTitle());
            msv.setViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.getSPMList(addressM.getCity());
        }
    }

    public void onEvent(AMapLocationUpdateEvent event) {
        if (null != event.getaMapLocation()) {
            HomeAddressM address = new HomeAddressM();
            address.setTitle(event.getaMapLocation().getPoiName());
            address.setCity(event.getaMapLocation().getCity());
            address.setAddress(event.getaMapLocation().getAddress());
            address.setLatLng(new LatLonPoint(event.getaMapLocation().getLatitude(), event.getaMapLocation().getLongitude()));
            addressM = address;
            if (AbStrUtil.isEmpty(address.getTitle())) {
                tvAddress.setText(getString(R.string.loc_fail));
//                presenter.getSPMList("北京市");
            } else {
                presenter.getSPMList(addressM.getCity());
                tvAddress.setText(address.getTitle());
                return;
            }
        } else {
            addressM = null;
            tvAddress.setText(getString(R.string.loc_fail));
//            presenter.getSPMList("北京市");
        }
        loginFailed();
    }

    @Override
    public void onRefresh() {
        if (null != addressM) {
            presenter.getSPMList(addressM.getCity());
        } else {
            showToast("位置错误");
            loginFailed();
        }

    }

    @Override
    public void onLoadMoreRequested() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadMoreEnd(true);
            }
        }, 100);
    }

    @Override
    public void init() {

    }

    @Override
    public void loginSuccess(List<MerchantM> datas) {
        List<MerchantM> items = new ArrayList<>();
        refreshLayout.setRefreshing(false);
        for (int i = 0; i < datas.size(); i++) {
            LatLng shop_l = new LatLng(Double.valueOf(datas.get(i).getLat()), Double.valueOf(datas.get(i).getLng()));
            LatLng location_l = new LatLng(Double.valueOf(addressM.getLatLng().getLatitude()), Double.valueOf(addressM.getLatLng().getLongitude()));
            float distance = AMapUtils.calculateLineDistance(shop_l, location_l);
            float distruF = Float.valueOf(datas.get(i).getConfines());
            datas.get(i).setDistance(distruF);
            if (distruF >= distance) {
                items.add(datas.get(i));
            }
        }
        mAdapter.setNewData(items);
        if (mAdapter.getItemCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }

    }

    @Override
    public void loginFailed() {
        refreshLayout.setRefreshing(false);
        mAdapter.setNewData(new ArrayList<MerchantM>());
        msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }
}