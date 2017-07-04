package com.ytxd.spp.ui.activity.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.systembar.SystemBarHelper;
import com.kennyc.view.MultiStateView;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.BaseActivity2;
import com.ytxd.spp.event.CartListClearRefreshEvent;
import com.ytxd.spp.event.CartListDialogShowEvent;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.event.MerchantSelectGoodStandEvent;
import com.ytxd.spp.model.CatagaryM;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.presenter.MerchantPresenter;
import com.ytxd.spp.ui.activity.order.EnsureOrderActivity;
import com.ytxd.spp.ui.activity.order.ShoppingCartActivity;
import com.ytxd.spp.ui.adapter.MerchantCategoryA;
import com.ytxd.spp.ui.adapter.MerchantGoodA;
import com.ytxd.spp.ui.views.MyExpandableLayout;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.ui.views.pop.MerchantCartListDialog;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.view.IMerchantView;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MerchantDetailActivity extends BaseActivity2<MerchantPresenter> implements IMerchantView {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_good)
    RecyclerView rvGood;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout shopping_cart_bottom;
    @BindView(R.id.rl_cart)
    RelativeLayout rl_cart;
    MerchantCartListDialog cartListDialog;
    /*    @BindView(R.id.rl_cart_list)
        RelativeLayout rlCartList;*/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.icon)
    RoundedImageView icon;

    @BindView(R.id.tv_total_p)
    TextView tvTotalP;
    @BindView(R.id.tv_total_c)
    TextView tvTotalNum;
    String merchantCode;
    @BindView(R.id.tv_rb_score)
    TextView tvRbScore;
    @BindView(R.id.tv_month_sales)
    TextView tvMonthSales;
    @BindView(R.id.tv_qisong_p)
    TextView tvQisongP;
    @BindView(R.id.tv_distri_p)
    TextView tvDistriP;
    @BindView(R.id.tv_per_peo_p)
    TextView tvPerPeoP;
    @BindView(R.id.expand)
    MyExpandableLayout expand;
    @BindView(R.id.tv_acti_num)
    TextView tvActiNum;

    MerchantGoodA goodA;
    MerchantCategoryA categoryA;
    StickyHeaderLayoutManager goodLM;
    LinearLayoutManager categoryLM;
    MerchantM merchantM;
    @BindView(R.id.btn_ok)
    Button btnOk;
    String orderCode;
    public List<OrderGoodM> orderGoods = new ArrayList<>();
    public boolean isAgain = false;

    @Override
    protected void initPresenter() {
        presenter = new MerchantPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        ButterKnife.bind(this);
        SystemBarHelper.immersiveStatusBar(this, 0f);
        merchantM = (MerchantM) getIntent().getSerializableExtra("data");
        merchantCode = merchantM.getSupermarketCode();
        orderCode = getIntent().getStringExtra("orderCode");
        if (!AbStrUtil.isEmpty(orderCode)) {
            isAgain = true;
            presenter.getGoodFromOrder(orderCode);
        } else {
            isAgain = false;
            presenter.getGoodList(merchantCode);
        }
        initViews();

    }

    private void initViews() {
        ImageLoadUtil.setImageNP(merchantM.getLogoUrl(), icon, this);
        if (!AbStrUtil.isEmpty(merchantM.getHJUrl())) {
            ImageLoadUtil.setImageNP(merchantM.getHJUrl(), ivBg, this);
        } else if (!AbStrUtil.isEmpty(merchantM.getLogoUrl())) {
            ImageLoadUtil.setImageNP(merchantM.getLogoUrl(), ivBg, this);
        }
        if (null != merchantM.getManJian()) {
            setActiviesData(merchantM.getManJian());
        } else {
            presenter.getManjian(merchantCode);
        }
        tvQisongP.setText("¥" + merchantM.getQSPrice() + "起送");
        tvDistriP.setText("配送费¥" + merchantM.getPSPrice());
        toolbar.setTitle(merchantM.getName());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //method 1
        SystemBarHelper.immersiveStatusBar(this, 0);
        SystemBarHelper.setHeightAndPadding(this, toolbar);

        goodA = new MerchantGoodA(this, merchantM);
        goodLM = new StickyHeaderLayoutManager();
        rvGood.setLayoutManager(goodLM);
        rvGood.addItemDecoration(new SimpleDividerDecoration(this, R.color.line_gray));
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
        categoryLM = new LinearLayoutManager(this);
        rvCategory.setLayoutManager(categoryLM);
        rvCategory.addItemDecoration(new SimpleDividerDecoration(this, R.color.line_gray));
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

    private void setActiviesData(List<MerchantM.ManJianBean> actis) {
        actis = merchantM.getManJian();
        tvActiNum.setText(actis.size() + "个活动");
        if (actis.size() > 0 && actis.size() <= 2) {
            for (int i = 0; i < actis.size(); i++) {
                TextView tvActi = (TextView) getLayoutInflater().inflate(R.layout.item_merchant_activity2, null);
                tvActi.setText(actis.get(i).getManJianName());
                ViewGroup header = (ViewGroup) expand.getHeaderRelativeLayout().findViewById(R.id.ll_ex_header);
                header.addView(tvActi);
            }

        } else if (actis.size() > 2) {
            List<MerchantM.ManJianBean> one = actis.subList(0, 2);
            List<MerchantM.ManJianBean> two = actis.subList(2, actis.size());
            for (int i = 0; i < one.size(); i++) {
                TextView tvActi = (TextView) getLayoutInflater().inflate(R.layout.item_merchant_activity2, null);
                tvActi.setText(one.get(i).getManJianName());
                ViewGroup header = (ViewGroup) expand.getHeaderRelativeLayout().findViewById(R.id.ll_ex_header);
                header.addView(tvActi);
            }

            for (int i = 0; i < two.size(); i++) {
                TextView tvActi = (TextView) getLayoutInflater().inflate(R.layout.item_merchant_activity2, null);
                tvActi.setText(two.get(i).getManJianName());
                ViewGroup content = (ViewGroup) expand.getContentRelativeLayout().findViewById(R.id.ll_ex_content);
                content.addView(tvActi);
            }
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


    public void onEvent(GoodAddEvent event) {
        if (null != event.getView()) {
            ShoppingCartUtil.addGoodAnimation(this, event.getView(), shoppingCart, toolbar, mainLayout);
        }
        if (event.type == 1) {
            if (ShoppingCartUtil.goodAddEvent(this, merchantM, event.getGoodM())) {
                refreshCartLayoutData();
            }
        } else {
            refreshCartLayoutData();
        }

    }

    public void onEvent(GoodMinusEvent event) {
        if (event.type == 1) {
            if (ShoppingCartUtil.goodMinusEvent(this, merchantM.getSupermarketCode(), event.getGoodM())) {
                refreshCartLayoutData();
            }
        } else {
            refreshCartLayoutData();
        }
    }

    public void refreshCartLayoutData() {
        QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                .whereEquals(LocalShoppingCartM.CARTCODE, merchantM.getSupermarketCode());
        List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            shoppingCartM.getShoppingCartM().setMerchantM(merchantM);
            App.liteOrm.update(shoppingCartM);
            int count = shoppingCartM.getShoppingCartM().getGoodsCounts();
            if (count != 0) {
                tvTotalNum.setText(count + "");
                tvTotalP.setText("共计¥" + shoppingCartM.getShoppingCartM().getPirceTotal());
                float qs = Float.valueOf(merchantM.getQSPrice());
                float p = Float.valueOf(shoppingCartM.getShoppingCartM().getPirceTotal());
                if (p >= qs) {
                    btnOk.setEnabled(true);
                } else {
                    btnOk.setEnabled(false);
                }
            } else {
                tvTotalNum.setText("0");
                tvTotalP.setText(CommonUtils.getString(R.string.none_goods));
                btnOk.setEnabled(false);
            }
            if (null != cartListDialog) {
                cartListDialog.setData();
            }
        } else {
            tvTotalNum.setText("0");
            tvTotalP.setText(CommonUtils.getString(R.string.none_goods));
            btnOk.setEnabled(false);
        }
        if (null != goodA) {
            goodA.notifyDataSetChanged();
        }


    }
    public void refreshCartLayoutData2() {
        QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                .whereEquals(LocalShoppingCartM.CARTCODE, merchantM.getSupermarketCode());
        List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            int count = shoppingCartM.getShoppingCartM().getGoodsCounts();
            if (count != 0) {
                tvTotalNum.setText(count + "");
                tvTotalP.setText("共计¥" + shoppingCartM.getShoppingCartM().getPirceTotal());
                float qs = Float.valueOf(merchantM.getQSPrice());
                float p = Float.valueOf(shoppingCartM.getShoppingCartM().getPirceTotal());
                if (p >= qs) {
                    btnOk.setEnabled(true);
                } else {
                    btnOk.setEnabled(false);
                }
                if (null != cartListDialog) {
                    cartListDialog.setData();
                }
            } else {
                tvTotalNum.setText("0");
                tvTotalP.setText(CommonUtils.getString(R.string.none_goods));
                btnOk.setEnabled(false);
            }
        } else {
            tvTotalNum.setText("0");
            tvTotalP.setText(CommonUtils.getString(R.string.none_goods));
            btnOk.setEnabled(false);
        }
    }


    @OnClick({R.id.btn_ok, R.id.shopping_cart_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (!tvTotalNum.getText().toString().equals("0")) {
                    startActivity(EnsureOrderActivity.class, "merchantCode", merchantCode);
                }
                break;
            case R.id.shopping_cart_layout:
                showCart();
                break;
        }
    }

    private void showCart() {
        if (null == cartListDialog) {
            cartListDialog = new MerchantCartListDialog(this, merchantM, 1);
        }
        Window window = cartListDialog.getWindow();
        cartListDialog.setCanceledOnTouchOutside(true);
        cartListDialog.setCancelable(true);
        cartListDialog.show();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        params.dimAmount = 0.3f;
        window.setAttributes(params);
    }

    public void onEvent(CartListDialogShowEvent event) {
        if (event.show) {
            rl_cart.setVisibility(View.INVISIBLE);
        } else {
            rl_cart.setVisibility(View.VISIBLE);
        }

    }

    public void onEvent(CartListClearRefreshEvent event) {
        refreshCartLayoutData();
        goodA.notifyDataSetChanged();
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
            rl_cart.setVisibility(View.VISIBLE);
        } else {
            rl_cart.setVisibility(View.GONE);
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        for (int i = 0; i < items.size(); i++) {
            for (int i1 = 0; i1 < items.get(i).getChildren().size(); i1++) {
                ShoppingCartUtil.refreshLocalCartGood(items.get(i).getChildren().get(i1), merchantCode);
            }
        }
    }

    @Override
    public void lodeManjianSuccess(List<MerchantM.ManJianBean> items) {
        merchantM.setManJian(items);
        setActiviesData(items);
    }

    @Override
    public void lodeOrderGoodsSuccess(List<OrderGoodM> items) {
        this.orderGoods = items;
        if (items.size() > 0) {
            ShoppingCartUtil.deleteCart(this, merchantCode);
        }
        presenter.getGoodList(merchantCode);
    }

    @Override
    public void lodeOrderGoodsFail() {
        presenter.getGoodList(merchantCode);
    }

    @Override
    public void lodeFailed() {
        rl_cart.setVisibility(View.GONE);
        msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    public void onEvent(MerchantSelectGoodStandEvent event) {
        ShoppingCartUtil.showGoodStandDialog(1, merchantCode, event.goodM, getFragmentManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_merchant_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                startActivity(ShoppingCartActivity.class);
                break;

            case R.id.more:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshCartLayoutData();
        if (null != goodA) {
            goodA.notifyDataSetChanged();
        }
    }
}
