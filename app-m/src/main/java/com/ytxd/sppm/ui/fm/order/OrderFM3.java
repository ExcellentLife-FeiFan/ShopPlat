package com.ytxd.sppm.ui.fm.order;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.BaseFragment;
import com.ytxd.sppm.event.AceOrderSuccessEvent;
import com.ytxd.sppm.event.SelectStaffEvent;
import com.ytxd.sppm.event.SetSenddingSuccessEvent;
import com.ytxd.sppm.model.OrderM;
import com.ytxd.sppm.presenter.OrderFMPresenter;
import com.ytxd.sppm.ui.adapter.HomeOrderA;
import com.ytxd.sppm.ui.views.SimpleDividerDecoration;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.view.IOrderFMView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.greenrobot.event.EventBus;


/**
 * Created by apple on 2017/3/29.
 */

public class OrderFM3 extends BaseFragment<OrderFMPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IOrderFMView {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private Unbinder unbinder;
    private HomeOrderA mAdapter;
    private int page = 1;


    @Override
    protected void initPresenter() {
        presenter = new OrderFMPresenter(activity, this);
        presenter.init();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fm_order_1;
    }

    @Override
    public void initView() {
        swipeLayout.setOnRefreshListener(this);
        rv.setLayoutManager(new LinearLayoutManager(activity));
        rv.addItemDecoration(new SimpleDividerDecoration(activity, R.color.transparent, R.dimen.divider_height3));
        mAdapter = new HomeOrderA(null,presenter);
        mAdapter.setOnLoadMoreListener(this, rv);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                startActivity(MerchantDetailActivity.class, "data", mAdapter.getItem(i));
            }
        });
        presenter.getOrderList(CommonUtils.REFRESH, page,OrderM.HAVE_ACE_WATING_SEND);
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


    @Override
    public void refreshSuccess(List<OrderM> items) {
        swipeLayout.setRefreshing(false);
        mAdapter.setNewData(items);
        page = 1;
        showContent();
    }

    @Override
    public void aceOrderSuccess(int position) {

    }
    @Override
    public void setSenddingSuccess(int position) {
        mAdapter.getItem(position).setOrderStateCode(OrderM.SENDING);
        mAdapter.notifyItemChanged(position);
        EventBus.getDefault().post(new SetSenddingSuccessEvent(mAdapter.getItem(position)));
    }
    @Override
    public void cancelSuccess(int position) {
    }

    @Override
    public void ensureSuccess(int position) {

    }

    @Override
    public void refundSuccess(int position) {

    }

    @Override
    public void refreshFailed() {
        swipeLayout.setRefreshing(false);
        showContent();
    }


    @Override
    public void lodeMoreSuccess(List<OrderM> items) {
        mAdapter.addData(items);
        mAdapter.loadMoreComplete();
        showContent();
    }

    public void onEvent(SelectStaffEvent event) {
        OrderM order=mAdapter.getItem(event.position);
        order.setDeliveryStaffCode(event.deliveryStaffM.getDeliveryStaffCode());
        order.setDeliveryStaffModel(event.deliveryStaffM);
        mAdapter.notifyItemChanged(event.position);
    }



    @Override
    public void lodeMoreFailed() {
        page--;
        mAdapter.loadMoreEnd();
        showContent();
    }

    @Override
    public void onRefresh() {
        presenter.getOrderList(CommonUtils.REFRESH, 1,OrderM.HAVE_ACE_WATING_SEND);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getOrderList(CommonUtils.LODEMORE, ++page,OrderM.HAVE_ACE_WATING_SEND);
    }

    @Override
    public void init() {

    }

    private void showContent() {
        if (mAdapter.getItemCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    public void onEvent(AceOrderSuccessEvent event) {
        mAdapter.getData().add(0, event.orderM);
        mAdapter.notifyDataSetChanged();

    }

    public void onEvent(SetSenddingSuccessEvent event) {
        if (mAdapter.getData().remove(event.orderM)) {
            mAdapter.notifyDataSetChanged();
        }
        showContent();
    }
}