package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.OrderChangevent;
import com.ytxd.spp.event.RefreshOrderListEvent;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.OrderActivityPresenter;
import com.ytxd.spp.ui.adapter.HomeOrderA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.view.IOrderActivityView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends BaseActivity<OrderActivityPresenter> implements View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IOrderActivityView {


    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.msv_order)
    MultiStateView msvOrder;
    HomeOrderA mAdapter;
    int page = 1;


    @Override
    protected void initPresenter() {
        presenter = new OrderActivityPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        getBar().initActionBar("订单", this);
        refreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(activity, R.color.common_divider_color, R.dimen.divider_height));
        mAdapter = new HomeOrderA(null, presenter);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.isFirstOnly(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(activity, OrderDetailActivity.class);
                intent.putExtra("orderCode", mAdapter.getItem(i).getOrderCode());
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
        presenter.getOrderList(CommonUtils.REFRESH, page);
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
        presenter.getOrderList(CommonUtils.REFRESH, 1);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getOrderList(CommonUtils.LODEMORE, ++page);
    }

    @Override
    public void init() {

    }

    @Override
    public void refreshSuccess(List<OrderM> items) {
        refreshLayout.setRefreshing(false);
        mAdapter.setNewData(items);
        page = 1;
        showContent();
    }

    @Override
    public void refreshFailed() {
        refreshLayout.setRefreshing(false);
        showContent();
    }


    @Override
    public void lodeMoreSuccess(List<OrderM> items) {
        mAdapter.addData(items);
        mAdapter.loadMoreComplete();
        showContent();
    }

    @Override
    public void lodeMoreFailed() {
        page--;
        mAdapter.loadMoreEnd();
        showContent();
    }

    private void showContent() {
        if (mAdapter.getItemCount() > 0) {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msvOrder.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    public void onEvent(RefreshOrderListEvent event) {
        msvOrder.setViewState(MultiStateView.VIEW_STATE_LOADING);
        presenter.getOrderList(CommonUtils.REFRESH, 1);

    }

    public void onEvent(OrderChangevent event) {
        if (event.isEvaluate) {
            if (event.position != -1) {
                mAdapter.getItem(event.position).setIsEvaluate(1);
                mAdapter.notifyItemChanged(event.position);
            }
        } else {
            if (event.position != -1 && event.position < mAdapter.getItemCount() - 1) {
                mAdapter.getItem(event.position).setOrderStateCode(event.state);
                mAdapter.notifyItemChanged(event.position);
            }
        }

    }

    @Override
    public void cancelSuccess(int position) {
        mAdapter.getItem(position).setOrderStateCode(OrderM.CANCEL_U);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void ensureSuccess(int position) {
        mAdapter.getItem(position).setOrderStateCode(OrderM.SUCCESS);
        mAdapter.notifyItemChanged(position);

    }

    @Override
    public void deleteSuccess(int position) {
        mAdapter.getData().remove(position);
        mAdapter.notifyItemRemoved(position);
    }

    @Override
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dissmisDialogs() {
           dismissDialog();
    }
}
