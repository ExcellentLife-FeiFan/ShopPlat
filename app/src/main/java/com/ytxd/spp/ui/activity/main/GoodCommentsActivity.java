package com.ytxd.spp.ui.activity.main;

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
import com.ytxd.spp.model.GoodEvaluateM;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.presenter.GCommnetPresenter;
import com.ytxd.spp.ui.adapter.GoodCommentsA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.view.IGCommentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoodCommentsActivity extends BaseActivity<GCommnetPresenter> implements View.OnClickListener,BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,IGCommentView{


    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.msv)
    MultiStateView msv;
    GoodCommentsA mAdapter;
    private GoodM goodM;


    @Override
    protected void initPresenter() {
        presenter = new GCommnetPresenter(activity, this);
        presenter.init();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_comment);
        ButterKnife.bind(this);
        getBar().initActionBar("商品评价", this);
        goodM = (GoodM) getIntent().getSerializableExtra("data");
        refreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(activity, R.color.common_divider_color, R.dimen.divider_height));
        mAdapter = new GoodCommentsA(null);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
        presenter.getList(goodM.getGoodsCode());
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
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 100);
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
    public void lodeSuccess(List<GoodEvaluateM> items) {
        mAdapter.addData(items);
        if(mAdapter.getItemCount()>0){
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }else{
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void lodeFailed() {
        if(mAdapter.getItemCount()>0){
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }else{
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }
}
