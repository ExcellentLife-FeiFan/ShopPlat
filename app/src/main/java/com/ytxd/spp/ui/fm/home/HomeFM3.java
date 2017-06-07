package com.ytxd.spp.ui.fm.home;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.ui.activity.order.MyOrderActivity;
import com.ytxd.spp.ui.activity.order.OrderDetailActivity;
import com.ytxd.spp.ui.adapter.HomeOrderA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM3 extends BaseFragment implements  SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.msv_order)
    MultiStateView msvOrder;
    Unbinder unbinder;
    HomeOrderA orderA;

    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_3;
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshListener(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(activity));
        rvOrder.addItemDecoration(new SimpleDividerDecoration(activity, R.color.bg, R.dimen.divider_height3));
        orderA = new HomeOrderA(CommonUtils.getSampleList(7));
        orderA.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rvOrder.setAdapter(orderA);
//        rvOrder.addOnItemTouchListener(new OnItemClickListener() {
//            @Override
//            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                startActivity(OrderDetailActivity.class);
//            }
//        });
        orderA.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                startActivity(OrderDetailActivity.class);
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

    @OnClick(R.id.tv_all_order)
    public void onViewClicked() {
        startActivity(MyOrderActivity.class);
    }
    @Override
    public void onRefresh() {
        msvOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
                msvOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
        }, 1000);
    }
}
