package com.ytxd.spp.ui.fm.merchant;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.event.RefreshGoodRVEvent;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.presenter.MerchantGoodPresenter;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.adapter.MerchantCategoryA;
import com.ytxd.spp.ui.adapter.MerchantGoodA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.view.IMerchantGoodView;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by apple on 2017/3/29.
 */

public class MerchantGoodFM extends BaseFragment<MerchantGoodPresenter> implements IMerchantGoodView {
    Unbinder unbinder;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_good)
    RecyclerView rvGood;
    @BindView(R.id.msv)
    MultiStateView msv;

    MerchantGoodA goodA;
    MerchantCategoryA categoryA;
    StickyHeaderLayoutManager goodLM;
    LinearLayoutManager categoryLM;

    MerchantM merchantM;
    private String merchantCode;
    private String orderCode;
    public List<OrderGoodM> orderGoods = new ArrayList<>();
    public boolean isAgain = false;
    MerchantDetailActivity factivity;

    @Override
    protected void initPresenter() {
        presenter = new MerchantGoodPresenter(activity, this);
        presenter.init();
    }


    @Override
    public int getLayoutRes() {
        return R.layout.fm_merchant_good;
    }

    @Override
    public void initView() {
        merchantM = (MerchantM) getArguments().getSerializable("data");
        merchantCode = merchantM.getSupermarketCode();
        factivity = (MerchantDetailActivity) getActivity();
        orderCode = getArguments().getString("orderCode");
        if (!AbStrUtil.isEmpty(orderCode)) {
            isAgain = true;
            presenter.getGoodFromOrder(orderCode);
        } else {
            isAgain = false;
            presenter.getGoodList(merchantCode);
        }

        goodA = new MerchantGoodA(activity, merchantM, this);
        goodLM = new StickyHeaderLayoutManager();
        rvGood.setLayoutManager(goodLM);
        rvGood.addItemDecoration(new SimpleDividerDecoration(activity, R.color.line_gray));
        rvGood.setAdapter(goodA);
        rvGood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                MerchantGoodA.HeaderViewHolder headerViewHolder = (MerchantGoodA.HeaderViewHolder) goodLM.getFirstVisibleHeaderViewHolder(true);
                int section = headerViewHolder.getSection();
                categoryLM.scrollToPositionWithOffset(section, 0);
                categoryA.setPositionS(section);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        categoryA = new MerchantCategoryA(null);
        categoryLM = new LinearLayoutManager(activity);
        rvCategory.setLayoutManager(categoryLM);
        rvCategory.addItemDecoration(new SimpleDividerDecoration(activity, R.color.line_gray));
        rvCategory.setAdapter(categoryA);
        rvCategory.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                categoryA.setPositionS(position);
                CatagaryM setion = goodA.getSection(position);
                if (setion.getAdapterP() >= 0) {
                    goodLM.scrollToPosition(setion.getAdapterP());
                }
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


    @Override
    public void init() {

    }

    @Override
    public void lodeSuccess(List<CatagaryM> items) {
        setSectionAdapterPosition(items);
        categoryA.addData(items);
        goodA.addAll(items);
        if (goodA.getItemCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            factivity.showBottomCart();
        } else {
            factivity.dissmissBottomCart();
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        for (int i = 0; i < items.size(); i++) {
            for (int i1 = 0; i1 < items.get(i).getChildren().size(); i1++) {
                ShoppingCartUtil.refreshLocalCartGood(items.get(i).getChildren().get(i1), merchantCode);
                if (!AbStrUtil.isEmpty(orderCode) && orderGoods.size() > 0) {
                    presenter.addOrderGood(items.get(i).getChildren().get(i1), merchantM, orderGoods);
                }

            }
        }
        if (!AbStrUtil.isEmpty(orderCode)) {
            ((MerchantDetailActivity) activity).refreshCartLayoutData2();
        }
    }

    @Override
    public void lodeOrderGoodsSuccess(List<OrderGoodM> items) {
        this.orderGoods = items;
        if (items.size() > 0) {
            ShoppingCartUtil.deleteCart(activity, merchantCode);
        }
        presenter.getGoodList(merchantCode);
    }

    @Override
    public void lodeOrderGoodsFail() {
        presenter.getGoodList(merchantCode);
    }

    @Override
    public void lodeFailed() {
        factivity.dissmissBottomCart();
        msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    private void setSectionAdapterPosition(List<CatagaryM> categories) {
        int count = 0;
        for (int i = 0; i < categories.size(); i++) {
            CatagaryM one = categories.get(i);
            one.setAdapterP(count + 2 * i);
            count = count + one.getChildren().size();
        }

    }

    public void onEvent(RefreshGoodRVEvent event) {
        if (null != goodA) {
            goodA.notifyDataSetChanged();
        }
    }

}
