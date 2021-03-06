package com.ytxd.spp.ui.activity.mine;

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
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.presenter.DiscountCouponPresenter;
import com.ytxd.spp.ui.adapter.CouponA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.view.IDiscountCouponView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscountCouponActivity extends BaseActivity<DiscountCouponPresenter> implements View.OnClickListener, IDiscountCouponView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    CouponA mAdapter;
    private String isSelect, price;

    @Override
    protected void initPresenter() {
        presenter = new DiscountCouponPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_coupon);
        ButterKnife.bind(this);
        getBar().initActionBar("优惠券", "", "不适用优惠券", R.drawable.ic_back_white, -1, this);
        isSelect = getIntent().getStringExtra("isSelect");
        price = getIntent().getStringExtra("price");
        mAdapter = new CouponA(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        swipeLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(activity));
        rvList.addItemDecoration(new SimpleDividerDecoration(activity, R.color.transparent, R.dimen.divider_height3));
        rvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (!AbStrUtil.isEmpty(isSelect)) {
                    CouponM item = mAdapter.getItem(i);
                    float p = Float.valueOf(price);
                    float m = Float.valueOf(item.getMMoneyUse());
                    if (CommonUtils.getBoolean(item.getIsUse())) {
                        showToast("优惠券已使用");
                        return;
                    }
                    if (CommonUtils.isCouponPast(item)) {
                        showToast("优惠券已过期");
                        return;
                    }
                    if (p <= m) {
                        showToast("未满足使用条件");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("coupon", item);
                    setResult(1, intent);
                    AppManager.getInstance().killActivity(activity);
                }
//                startActivity(MerchantDetailActivity.class, "data", mAdapter.getItem(i));
            }
        });
        presenter.getList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
            case R.id.tv_right:
                Intent intent = new Intent();
                setResult(1, intent);
                AppManager.getInstance().killActivity(activity);
                break;
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void lodeSuccess(List<CouponM> items) {
        swipeLayout.setRefreshing(false);
        mAdapter.setNewData(items);
        if (mAdapter.getItemCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            presenter.setCouponRead();
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void lodeFailed() {
        swipeLayout.setRefreshing(false);
        if (mAdapter.getItemCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onRefresh() {
        presenter.getList();
    }
}
