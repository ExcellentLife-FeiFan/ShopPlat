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
import com.ytxd.spp.event.RefreshOrderListEvent;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.HomeOrderPresenter;
import com.ytxd.spp.ui.activity.order.MyOrderActivity;
import com.ytxd.spp.ui.activity.order.OrderDetailActivity;
import com.ytxd.spp.ui.adapter.HomeOrderA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.view.IHomeOrderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM3 extends BaseFragment<HomeOrderPresenter> implements SwipeRefreshLayout.OnRefreshListener, IHomeOrderView {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.rv_order)
    RecyclerView rvOrder;
    @BindView(R.id.msv_order)
    MultiStateView msvOrder;
    Unbinder unbinder;
    HomeOrderA orderA;

    @Override
    protected void initPresenter() {
        presenter = new HomeOrderPresenter(activity, this);
        presenter.init();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_3;
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshListener(this);
        rvOrder.setLayoutManager(new LinearLayoutManager(activity));
        rvOrder.addItemDecoration(new SimpleDividerDecoration(activity, R.color.common_divider_color, R.dimen.divider_height));
        orderA = new HomeOrderA(null);
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
                startActivity(OrderDetailActivity.class,"data",orderA.getItem(i));
            }
        });
        presenter.getOrderList();
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
        presenter.getOrderList();
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeSuccess(List<OrderM> items) {
        refreshLayout.setRefreshing(false);
        orderA.setNewData(items);
        if (orderA.getItemCount() > 0) {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void lodeFailed() {
        refreshLayout.setRefreshing(false);
        msvOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }


    public void onEvent(RefreshOrderListEvent event) {
        msvOrder.setViewState(MultiStateView.VIEW_STATE_LOADING);
        presenter.getOrderList();

    }
}
