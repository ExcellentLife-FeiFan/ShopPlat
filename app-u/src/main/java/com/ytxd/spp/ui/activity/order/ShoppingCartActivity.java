package com.ytxd.spp.ui.activity.order;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.adapter.ShoppingCartA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.msv)
    MultiStateView msv;
    ShoppingCartA mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        getBar().initActionBar("购物车", this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(activity, R.color.bg, R.dimen.divider_height3));
        mAdapter = new ShoppingCartA(null);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                startActivity(OrderDetailActivity.class);
            }
        });

        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.addData(CommonUtils.getSampleList(15));
                msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }
}
