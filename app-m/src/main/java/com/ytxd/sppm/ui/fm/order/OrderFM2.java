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


/**
 * Created by apple on 2017/3/29.
 */

public class OrderFM2 extends BaseFragment<OrderFMPresenter> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener, IOrderFMView {
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
        mAdapter = new HomeOrderA(null, presenter);
        mAdapter.setOnLoadMoreListener(this, rv);
//        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        rv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                startActivity(MerchantDetailActivity.class, "data", mAdapter.getItem(i));
            }
        });
        presenter.getOrderList(CommonUtils.REFRESH, page, OrderM.HAVE_PAY_WATING_ACE);
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

    @Override
    public void aceOrderSuccess(int position) {
        mAdapter.remove(position);
        mAdapter.notifyItemRemoved(position);
        showContent();
/*        mAdapter.getItem(position).setOrderStateCode(OrderM.HAVE_ACE_WATING_SEND);
        mAdapter.notifyItemChanged(position);
        EventBus.getDefault().post(new AceOrderSuccessEvent(mAdapter.getItem(position)));*/
    }

    @Override
    public void aceOrderFailed(int position, OrderM orderM) {
        mAdapter.remove(position);
        mAdapter.notifyItemRemoved(position);
        showContent();
    }

    @Override
    public void setSenddingSuccess(int position) {

    }

    @Override
    public void cancelSuccess(int position) {
        mAdapter.remove(position);
        mAdapter.notifyItemRemoved(position);
        showContent();
      /*  mAdapter.getItem(position).setOrderStateCode(OrderM.CANCEL_M);
        mAdapter.notifyItemChanged(position);
        EventBus.getDefault().post(new CancelSuccessEvent(mAdapter.getItem(position)));*/
    }

    @Override
    public void ensureSuccess(int position) {

    }

    @Override
    public void refundSuccess(int position) {

    }


    @Override
    public void lodeMoreFailed() {
        page--;
        mAdapter.loadMoreEnd();
        showContent();
    }

    @Override
    public void onRefresh() {
        presenter.getOrderList(CommonUtils.REFRESH, 1, OrderM.HAVE_PAY_WATING_ACE);
    }

    @Override
    public void onLoadMoreRequested() {
        presenter.getOrderList(CommonUtils.LODEMORE, ++page, OrderM.HAVE_PAY_WATING_ACE);
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

  /*  public void onEvent(AceOrderSuccessEvent event) {
        if (mAdapter.getData().remove(event.orderM)) {
            mAdapter.notifyDataSetChanged();
        }
        showContent();

    }

    public void onEvent(CancelSuccessEvent event) {
        if (mAdapter.getData().remove(event.orderM)) {
            mAdapter.notifyDataSetChanged();
        }
        showContent();
    }*/
    @Override
    protected void onVisible() {
        if(null!=presenter){
            onRefresh();
        }

    }

}