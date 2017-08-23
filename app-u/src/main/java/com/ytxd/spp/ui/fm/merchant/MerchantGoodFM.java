package com.ytxd.spp.ui.fm.merchant;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.event.GoodsChangeEvent;
import com.ytxd.spp.event.RefreshGoodRVEvent;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.presenter.MerchantGoodPresenter;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.adapter.MerchantCategoryA;
import com.ytxd.spp.ui.adapter.MerchantGoodA;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.DialogUtils;
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
//        goodA.r
        goodLM = new StickyHeaderLayoutManager();
        rvGood.setLayoutManager(goodLM);
        rvGood.addItemDecoration(new SimpleDividerDecoration(activity, R.color.line_gray));
        rvGood.setAdapter(goodA);
        rvGood.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                try {
                    MerchantGoodA.HeaderViewHolder headerViewHolder = (MerchantGoodA.HeaderViewHolder) goodLM.getFirstVisibleHeaderViewHolder(true);
                    int section = headerViewHolder.getSection();
                    categoryLM.scrollToPositionWithOffset(section, 0);
                    categoryA.setPositionS(section);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                if (position == categoryA.getItemCount() - 1) {
                    goodA.getSection(0).getChildren().remove(0);
                    goodA.notifySectionDataSetChanged(0);
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
                GoodM goodM = items.get(i).getChildren().get(i1);
                //把商品一个一个存入本地数据库，用来店内商品搜索
                if (null != goodM.getGoods() && goodM.getGoods().size() > 0) {
                    for (int i2 = 0; i2 < goodM.getGoods().size(); i2++) {
                        App.liteOrm.save(goodM.getGoods().get(i2));
                    }
                } else {
                    App.liteOrm.save(goodM);
                }
                /////////////////////////////////////////////////////////////////
                ShoppingCartUtil.updateLocalCartGood(goodM, merchantCode);
                if (!AbStrUtil.isEmpty(orderCode) && orderGoods.size() > 0) {
                    presenter.addOrderGood(activity, goodM, merchantM, orderGoods);
                }

            }
        }

        if (!AbStrUtil.isEmpty(orderCode)) {
            //如orderCode不为空，那么将处理再来一单的添加商品到本地购物车的逻辑
            //之前逐个对照新的商品列表添加到本地购物车时（presenter.addOrderGood方法），如果新获取的商品列表存在，则orderGoods中移除该商品，
            // 如果所有对照结束后orderGoods中剩下的商品说明就是新商品列表中不逊在的，那么它们将不会添加到本地购物车
            if (orderGoods.size() > 0) {
                for (int i = 0; i < orderGoods.size(); i++) {
                    orderGoods.get(i).setChangeType(1);
                    ((MerchantDetailActivity) activity).oneMoreOrderGoodsChanged.add(orderGoods.get(i));
                }
            }//////////////////////////////////////////////////
            ((MerchantDetailActivity) activity).refreshCartLayoutData2();
            ((MerchantDetailActivity) activity).showCart();
            List<GoodM> gChanges = ((MerchantDetailActivity) activity).oneMoreOrderGoodsChanged;
            if (gChanges.size() > 0) {
                String changes = "";
                for (int i = 0; i < gChanges.size(); i++) {
                    changes = changes + gChanges.get(i).getGoodsTitle() + (gChanges.get(i).changeType == 1 ? "已删除" : "价格已改变")+"\n";
                }

                DialogUtils.showDialog(activity, "提示", "\"再来一单\"\n" + changes,"确定","" ,new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which.name().equals(DialogAction.POSITIVE.name())) {

                        }
                    }
                });
            }
        } else {
            if (ShoppingCartUtil.refreshLocalCartGoodByOnlineGot(merchantCode)) {
                ((MerchantDetailActivity) activity).refreshCartLayoutData2();
            }
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
        try {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void onEvent(GoodsChangeEvent event) {
        if (event.goods.size() > 0) {
            for (int j = 0; j < event.goods.size(); j++) {
                GoodM good = event.goods.get(j);
                if (good.getChangeType() == 1) {
                    ShoppingCartUtil.deleteLocalCartGood(good, merchantCode);
                } else if (good.getChangeType() == 2) {
                    ShoppingCartUtil.updateLocalCartGood(good, merchantCode);
                }
                int sectionIndex = -1;
                searchGood:
                {
                    for (int i = 0; i < goodA.getNumberOfSections(); i++) {
                        if (goodA.getSection(i).getGoodsTypeCode().equals(good.getGoodsTypeCode())) {
                            List<GoodM> cgs = goodA.getSection(i).getChildren();
                            for (int l = 0; l < cgs.size(); l++) {
                                GoodM gn = cgs.get(l);
                                if (null != gn.getGoods() && gn.getGoods().size() > 0) {
                                    for (int k = 0; k < gn.getGoods().size(); k++) {
                                        if (gn.getGoods().get(k).getGoodsCode().equals(good.getGoodsCode())) {
                                            if (good.getChangeType() == 1) {
                                                gn.getGoods().remove(k);
                                            } else if (good.getChangeType() == 2) {
                                                gn.getGoods().set(k, good);
                                            }
                                            sectionIndex = i;
                                            break searchGood;
                                        }
                                    }
                                } else {
                                    if (gn.getGoodsCode().equals(good.getGoodsCode())) {
                                        if (good.getChangeType() == 1) {
                                            cgs.remove(l);
                                        } else if (good.getChangeType() == 2) {
                                            cgs.set(l, good);
                                        }
                                        sectionIndex = i;
                                        break searchGood;
                                    }
                                }
                            }
                        }
                    }
                }
                if (sectionIndex != -1) {
                    goodA.notifySectionDataSetChanged(sectionIndex);
                }
            }
            ((MerchantDetailActivity) activity).refreshCartLayoutData2();
            DialogUtils.showDialog(activity, "提示", "变化商品已更新，可以接着下单", "好的", "", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which.name().equals(DialogAction.POSITIVE.name())) {
                    }
                }
            });

        }
    }


}
