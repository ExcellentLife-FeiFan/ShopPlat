package com.ytxd.spp.ui.fm.home;


import android.content.Intent;
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
import com.ytxd.spp.event.OrderChangevent;
import com.ytxd.spp.event.RefreshLoginEvent;
import com.ytxd.spp.event.RefreshOrderListEvent;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.HomeOrderPresenter;
import com.ytxd.spp.ui.activity.login.LoginActivity;
import com.ytxd.spp.ui.activity.order.MyOrderActivity;
import com.ytxd.spp.ui.activity.order.OrderDetailActivity;
import com.ytxd.spp.ui.adapter.HomeOrderA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.CommonUtils;
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
    View footerView;

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
        footerView = activity.getLayoutInflater().inflate(R.layout.footer_oder_more, (ViewGroup) rvOrder.getParent(), false);
        footerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MyOrderActivity.class);
            }
        });
        rvOrder.addItemDecoration(new SimpleDividerDecoration(activity, R.color.common_divider_color, R.dimen.divider_height));
        orderA = new HomeOrderA(null, presenter);
        orderA.addFooterView(footerView);
        orderA.isFirstOnly(true);
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
                Intent intent = new Intent(activity, OrderDetailActivity.class);
                intent.putExtra("orderCode", orderA.getItem(i).getOrderCode());
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
        msvOrder.getView(MultiStateView.VIEW_STATE_ERROR).findViewById(R.id.tv_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.class);
            }
        });
        if (CommonUtils.isLogined2()) {
            presenter.getOrderList();
        } else {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
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
        if (CommonUtils.isLogined(activity)) {
            startActivity(MyOrderActivity.class);
        }
    }

    @Override
    public void onRefresh() {
        if (CommonUtils.isLogined(activity)) {
            presenter.getOrderList();
        } else {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void cancelSuccess(int position) {
        orderA.getItem(position).setOrderStateCode(OrderM.CANCEL_U);
        orderA.notifyItemChanged(position);
    }

    @Override
    public void ensureSuccess(int position) {
        orderA.getItem(position).setOrderStateCode(OrderM.SUCCESS);
        orderA.notifyItemChanged(position);
    }

    @Override
    public void deleteSuccess(int position) {
        orderA.getData().remove(position);
        orderA.notifyItemRemoved(position);
    }

    @Override
    public void refreshSuccess(List<OrderM> items) {
        refreshLayout.setRefreshing(false);
        orderA.setNewData(items);
        showContent();
    }

    @Override
    public void refreshFailed() {
        refreshLayout.setRefreshing(false);
        showContent();
    }

    @Override
    public void showDialogs() {
        CommonUtils.showDialog(activity);
    }

    @Override
    public void dissmisDialogs() {
        CommonUtils.hideDialog();
    }

    private void showContent() {
        if (orderA.getData().size() > 0) {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (orderA.getData().size() == 3) {
                if (orderA.getFooterLayoutCount() == 0) {
                    orderA.addFooterView(footerView);
                }
            } else {
                orderA.removeAllFooterView();
            }
        } else {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }


    public void onEvent(RefreshOrderListEvent event) {
        msvOrder.setViewState(MultiStateView.VIEW_STATE_LOADING);
        presenter.getOrderList();
    }

    public void onEvent(RefreshLoginEvent event) {
        if (CommonUtils.isLogined2()) {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_LOADING);
            presenter.getOrderList();
        }
    }

    public void onEvent(OrderChangevent event) {
        if (event.isEvaluate) {
            if (event.position != -1) {
                orderA.getItem(event.position).setIsEvaluate(1);
                orderA.notifyItemChanged(event.position);
            }
        } else {
            if (event.position != -1 && event.position < orderA.getItemCount() - 1) {
                orderA.getItem(event.position).setOrderStateCode(event.state);
                orderA.notifyItemChanged(event.position);
            }
        }
    }
}
