package com.ytxd.spp.ui.fm.merchant;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.model.MerchantEvaluateM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.presenter.MCommnetPresenter;
import com.ytxd.spp.ui.adapter.MerchantCommentA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.view.IMCommentView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by apple on 2017/3/29.
 */

public class MerchantEvaluateFM extends BaseFragment<MCommnetPresenter> implements BaseQuickAdapter.RequestLoadMoreListener,IMCommentView{

    Unbinder unbinder;
    TextView tvMerchantScore;
    MaterialRatingBar rbDistr;
    TextView tvDistrScore;
    MaterialRatingBar rbGood;
    TextView tvGoodScore;
    @BindView(R.id.rv)
    RecyclerView mRecyclerView;
    @BindView(R.id.msv)
    MultiStateView msv;
    MerchantCommentA mAdapter;
    private MerchantM merchantM;

    @Override
    protected void initPresenter() {
        presenter = new MCommnetPresenter(activity, this);
        presenter.init();
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fm_merchant_evaluate;
    }

    @Override
    public void initView() {
        merchantM= (MerchantM) getArguments().getSerializable("data");
        View headerView = activity.getLayoutInflater().inflate(R.layout.header_merchant_evaluate, (ViewGroup) mRecyclerView.getParent(), false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        mRecyclerView.addItemDecoration(new SimpleDividerDecoration(activity, R.color.common_divider_color, R.dimen.divider_height));
        mAdapter = new MerchantCommentA(null);
        mAdapter.addHeaderView(headerView);
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                startActivity(OrderDetailActivity.class,"data",mAdapter.getItem(i));
            }
        });
        presenter.getList(merchantM.getSupermarketCode());
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
    public void onLoadMoreRequested() {
      mAdapter.loadMoreEnd(true);
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeSuccess(List<MerchantEvaluateM> items) {
        mAdapter.addData(items);
        if(mAdapter.getItemCount()>1){
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
