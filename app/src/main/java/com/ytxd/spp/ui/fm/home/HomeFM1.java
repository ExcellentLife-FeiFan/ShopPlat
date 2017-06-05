package com.ytxd.spp.ui.fm.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.event.AMapLocationUpdateEvent;
import com.ytxd.spp.event.HomeAddressChangeEvent;
import com.ytxd.spp.model.HomeAddressM;
import com.ytxd.spp.ui.activity.main.HomeSearchActivity;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.activity.main.SelectACEAddressActivity;
import com.ytxd.spp.ui.adapter.HomeMerchantA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.AMapLocationUtil;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM1 extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{

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
    HomeAddressM addressM;
    HomeMerchantA mAdapter;


    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_1;
    }

    @Override
    public void initView() {
        AMapLocationUtil.getInstance().startLocation();

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(activity, R.color.line_gray, R.dimen.common_divider_height));
        mAdapter = new HomeMerchantA(CommonUtils.getSampleList(15));
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                startActivity(MerchantDetailActivity.class);
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
        }
    }

    public void onEvent(AMapLocationUpdateEvent event) {
        if (null != event.getaMapLocation()) {
            HomeAddressM address = new HomeAddressM();
            address.setTitle(event.getaMapLocation().getPoiName());
            address.setAddress(event.getaMapLocation().getAddress());
            address.setLatLng(new LatLonPoint(event.getaMapLocation().getLatitude(), event.getaMapLocation().getLongitude()));
            addressM = address;
            if(AbStrUtil.isEmpty(address.getTitle())){
                tvAddress.setText(getString(R.string.loc_fail));
            }else{
                tvAddress.setText(address.getTitle());
            }
        } else {
            addressM = null;
            tvAddress.setText(getString(R.string.loc_fail));
        }
    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onLoadMoreRequested() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadMoreEnd(true);
            }
        }, 3000);
    }
}