package com.ytxd.spp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.flyco.systembar.SystemBarHelper;
import com.flyco.tablayout.SlidingTabLayout;
import com.kennyc.view.MultiStateView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiushui.blurredview.BlurredView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity2;
import com.ytxd.spp.event.CartListClearRefreshEvent;
import com.ytxd.spp.event.CartListDialogShowEvent;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.event.MerchantSelectGoodStandEvent;
import com.ytxd.spp.event.OrderChangeToCannotBuyEvent;
import com.ytxd.spp.event.RefreshGoodRVEvent;
import com.ytxd.spp.event.RefreshLoginEvent;
import com.ytxd.spp.event.RefreshMerchantDataEvent;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.presenter.MerchantPresenter;
import com.ytxd.spp.ui.activity.order.EnsureOrderActivity;
import com.ytxd.spp.ui.activity.order.ShoppingCartActivity;
import com.ytxd.spp.ui.dialog.GoodSearchPop;
import com.ytxd.spp.ui.dialog.MerchantCartListDialog;
import com.ytxd.spp.ui.fm.merchant.MerchantEvaluateFM;
import com.ytxd.spp.ui.fm.merchant.MerchantGoodFM;
import com.ytxd.spp.ui.views.MyExpandableLayout;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.PixelUtil;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.view.IMerchantView;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MerchantDetailActivity extends BaseActivity2<MerchantPresenter> implements IMerchantView, SwipeRefreshLayout.OnRefreshListener {

    /*    @BindView(R.id.iv_bg)
        ImageView ivBg;*/
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swl;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout shopping_cart_bottom;
    @BindView(R.id.rl_cart)
    RelativeLayout rlCart;
    MerchantCartListDialog cartListDialog;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.icon)
    RoundedImageView icon;

    @BindView(R.id.tv_notice)
    TextView tvNotice;
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
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;


    MerchantM merchantM;
    String orderCode;
    @BindView(R.id.rl_close)
    RelativeLayout rlClose;
    private String[] titles = {"商品", "评价"};
    @BindView(R.id.blurredview)
    BlurredView blurredview;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    private TopRightMenu mTopRightMenu;
    private GoodSearchPop goodSearchPop;
    private MyPagerAdapter pagerAdapter;
    public List<GoodM> oneMoreOrderGoodsChanged = new ArrayList<>();


    @OnClick({R.id.btn_ok, R.id.shopping_cart_layout, R.id.iv_cha})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (!tvTotalNum.getText().toString().equals("0")) {
                    if (CommonUtils.isLogined(this, true)) {
                        startActivity(EnsureOrderActivity.class, "merchant", merchantM);
                    }
                }
                break;
            case R.id.shopping_cart_layout:
                showCart();
                break;
            case R.id.iv_cha:
                AppManager.getInstance().killActivity(this);
                break;
        }
    }

    @Override
    protected void initPresenter() {
        presenter = new MerchantPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        HideUtil.init(this);
        ButterKnife.bind(this);
        SystemBarHelper.immersiveStatusBar(this, 0f);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        merchantCode = getIntent().getStringExtra("merchantCode");
        presenter.getMerchant(merchantCode, true);
        orderCode = getIntent().getStringExtra("orderCode");
        initViews();
    }

    private void initViews() {
        swl.setOnRefreshListener(this);
        setSupportActionBar(toolbar);
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    swl.setEnabled(true);
                } else {
                    swl.setEnabled(false);
                }
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTopRightMenu = new TopRightMenu(this);
        mTopRightMenu
                .setHeight(PixelUtil.dp2px(60f, this))     //默认高度480
                .setWidth(PixelUtil.dp2px(110f, this))      //默认宽度wrap_content
                .showIcon(false)     //显示菜单图标，默认为true
                .dimBackground(false)        //背景变暗，默认为true
                .needAnimationStyle(true)   //显示动画，默认为true
                .setAnimationStyle(R.style.TRM_ANIM_STYLE)
                .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.drawable.ic_home_search, "店内搜索"))
                .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
                    @Override
                    public void onMenuItemClick(int position) {
                        if (position == 0) {
                            if (null == goodSearchPop) {
                                goodSearchPop = new GoodSearchPop(activity, merchantM);
                            }
                            if (goodSearchPop.isShowing()) {
                                goodSearchPop.dismiss();
                            } else {
                                goodSearchPop.showAtLocation(mainLayout, Gravity.TOP, 0, 0);
                            }
                        }
                    }
                });
        vp.setOffscreenPageLimit(2);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    showBottomCart();
                } else {
                    dissmissBottomCart();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setData(MerchantM merchantM) {
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), merchantM);
        vp.setAdapter(pagerAdapter);
        tab.setViewPager(vp, titles);
        ImageLoadUtil.setImageNP(merchantM.getLogoUrl(), icon, this);
        if (!AbStrUtil.isEmpty(merchantM.getHJUrl())) {
            Glide.with(this)
                    .load(Apis.AddPATH(merchantM.getHJUrl()))
                    .error(R.color.img_bg)
                    .placeholder(R.color.img_bg)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            blurredview.setBlurredLevel(99);
                            blurredview.setBlurredImg(resource);
                        }
                    });
        } else if (!AbStrUtil.isEmpty(merchantM.getLogoUrl())) {
            Glide.with(this)
                    .load(Apis.AddPATH(merchantM.getLogoUrl()))
                    .error(R.color.img_bg)
                    .placeholder(R.color.img_bg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            blurredview.setBlurredLevel(99);
                            blurredview.setBlurredImg(resource);
                        }
                    });
        }
        setActiviesData(merchantM.getManJian());
        tvQisongP.setText("¥" + merchantM.getQSPrice() + "起送");
        tvDistriP.setText("配送费¥" + merchantM.getPSPrice());
        CommonUtils.setText(tvNotice, merchantM.getNotice());
        toolbar.setTitle(merchantM.getName());
        refreshCartLayoutData();
    }

    private void setData2(MerchantM merchantM) {
        ImageLoadUtil.setImageNP(merchantM.getLogoUrl(), icon, this);
        if (!AbStrUtil.isEmpty(merchantM.getHJUrl())) {
            Glide.with(this)
                    .load(Apis.AddPATH(merchantM.getHJUrl()))
                    .error(R.color.img_bg)
                    .placeholder(R.color.img_bg)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            blurredview.setBlurredLevel(99);
                            blurredview.setBlurredImg(resource);
                        }
                    });
        } else if (!AbStrUtil.isEmpty(merchantM.getLogoUrl())) {
            Glide.with(this)
                    .load(Apis.AddPATH(merchantM.getLogoUrl()))
                    .error(R.color.img_bg)
                    .placeholder(R.color.img_bg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            blurredview.setBlurredLevel(99);
                            blurredview.setBlurredImg(resource);
                        }
                    });
        }
        setActiviesData(merchantM.getManJian());
        tvQisongP.setText("¥" + merchantM.getQSPrice() + "起送");
        tvDistriP.setText("配送费¥" + merchantM.getPSPrice());
        CommonUtils.setText(tvNotice, merchantM.getNotice());
        toolbar.setTitle(merchantM.getName());
        refreshCartLayoutData();
    }


    private void setActiviesData(List<MerchantM.ManJianBean> actis) {
        tvActiNum.setText(actis.size() + "个活动");
        if (actis.size() > 0 && actis.size() <= 2) {
            ViewGroup header = (ViewGroup) expand.getHeaderRelativeLayout().findViewById(R.id.ll_ex_header);
            header.removeAllViews();
            for (int i = 0; i < actis.size(); i++) {
                TextView tvActi = (TextView) getLayoutInflater().inflate(R.layout.item_merchant_activity2, null);
                tvActi.setText(actis.get(i).getManJianName());
                header.addView(tvActi);
            }

        } else if (actis.size() > 2) {
            List<MerchantM.ManJianBean> one = actis.subList(0, 2);
            List<MerchantM.ManJianBean> two = actis.subList(2, actis.size());
            ViewGroup header = (ViewGroup) expand.getHeaderRelativeLayout().findViewById(R.id.ll_ex_header);
            header.removeAllViews();
            for (int i = 0; i < one.size(); i++) {
                TextView tvActi = (TextView) getLayoutInflater().inflate(R.layout.item_merchant_activity2, null);
                tvActi.setText(one.get(i).getManJianName());
                header.addView(tvActi);
            }
            ViewGroup content = (ViewGroup) expand.getContentRelativeLayout().findViewById(R.id.ll_ex_content);
            content.removeAllViews();
            for (int i = 0; i < two.size(); i++) {
                TextView tvActi = (TextView) getLayoutInflater().inflate(R.layout.item_merchant_activity2, null);
                tvActi.setText(two.get(i).getManJianName());
                content.addView(tvActi);
            }
        }
    }

    public void refreshCartLayoutData() {
        if (null == merchantM) {
            return;
        }
        List<LocalShoppingCartM> beans = ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
        btnOk.setText("选好了");
        btnOk.setEnabled(true);
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
                    btnOk.setText("满" + merchantM.getQSPrice() + "送");
                    btnOk.setEnabled(false);
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
        if (null != cartListDialog) {
            cartListDialog.setData();
        }
        String canBuyS = ShoppingCartUtil.canBuy(merchantM);
        if (!canBuyS.equals("OK")) {
            btnOk.setEnabled(false);
            btnOk.setText(canBuyS);
        }
        EventBus.getDefault().post(new RefreshGoodRVEvent());
    }

    public void refreshCartLayoutData2() {
        List<LocalShoppingCartM> beans = ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
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

    public void showCart() {
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

    @Override
    public void init() {

    }

/*    @Override
    public void lodeManjianSuccess(List<MerchantM.ManJianBean> items) {
   *//*     merchantM.setManJian(items);
        setActiviesData(items);
        showToast("满减信息已更新");*//*
    }

    @Override
    public void lodeStateSucess(MerchantStateM obj) {
//        merchantM.setShopsOpen(obj.getShopsOpen());
//        merchantM.setBusinessEndTime(obj.getBusinessEndTime());
//        merchantM.setBusinessBeginTime(obj.getBusinessBeginTime());
//        refreshCartLayoutData();
    }*/

    @Override
    public void lodeInfoSucess(MerchantM merchant, boolean isLodeGood) {
        this.merchantM = merchant;
        if (isLodeGood) {
            setData(merchant);
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            swl.setRefreshing(false);
        } else {
            dismissDialog();
            setData2(merchant);
        }
        if (ShoppingCartUtil.canBuy(merchant).equals("OK")) {
            rlClose.setVisibility(View.GONE);
        } else {
            rlClose.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void lodeInfoFailure(boolean isLodeGood) {
        if (isLodeGood) {
            if (null == merchantM) {
                msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            } else {
                msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            }
            swl.setRefreshing(false);
        } else {

        }

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {
        MerchantM merchant;

        public MyPagerAdapter(FragmentManager fm, MerchantM merchant) {
            super(fm);
            this.merchant = merchant;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                MerchantGoodFM fragment1 = new MerchantGoodFM();
                Bundle data = new Bundle();
                data.putSerializable("data", merchant);
                data.putString("orderCode", orderCode);
                fragment1.setArguments(data);
                return fragment1;
            } else if (position == 1) {
                MerchantEvaluateFM fragment2 = new MerchantEvaluateFM();
                Bundle data = new Bundle();
                data.putSerializable("data", merchant);
                fragment2.setArguments(data);
                return fragment2;
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
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
                mTopRightMenu.showAsDropDown(toolbar, toolbar.getWidth() - 200, -30);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showBottomCart() {
        if (rlCart.getVisibility() == View.VISIBLE) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlCart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlCart.startAnimation(animation);
    }

    public void dissmissBottomCart() {
        if (rlCart.getVisibility() == View.GONE) {
            return;
        }
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rlCart.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rlCart.startAnimation(animation);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001) {
            if (data.getBooleanExtra("payback", false)) {
                List<LocalShoppingCartM> beans = ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
                ShoppingCartUtil.deleteCart(this, merchantCode);
                App.initDataBase(this);
                ShoppingCartUtil.deleteCart(this, merchantCode);
                App.liteOrm.save(beans);
                startActivity(EnsureOrderActivity.class, "merchant", merchantM);
                EventBus.getDefault().post(new RefreshLoginEvent());
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshCartLayoutData();
        oneMoreOrderGoodsChanged.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        int l = App.liteOrm.delete(GoodM.class);
        oneMoreOrderGoodsChanged.clear();
    }

    public void onEvent(GoodAddEvent event) {
        if (null != event.getView()) {
            showBottomCart();
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

    public void onEvent(CartListDialogShowEvent event) {
        if (event.show) {
            rlCart.setVisibility(View.INVISIBLE);
        } else {
            rlCart.setVisibility(View.VISIBLE);
        }

    }

    public void onEvent(CartListClearRefreshEvent event) {
        refreshCartLayoutData();
        EventBus.getDefault().post(new RefreshGoodRVEvent());
        if (null != cartListDialog) {
            cartListDialog.dismiss();
        }
    }

    public void onEvent(RefreshMerchantDataEvent event) {
        showDialog();
        presenter.getMerchant(merchantCode, false);
    }


    public void onEvent(MerchantSelectGoodStandEvent event) {
        ShoppingCartUtil.showGoodStandDialog(1, merchantCode, event.goodM, getFragmentManager());
    }

    public void onEvent(OrderChangeToCannotBuyEvent event) {
   /*     if (ShoppingCartUtil.canBuy(merchantM).equals("OK")) {
            merchantM.setShopsOpen(event.stateM.getShopsOpen());
            merchantM.setBusinessEndTime(event.stateM.getBusinessEndTime());
            merchantM.setBusinessBeginTime(event.stateM.getBusinessBeginTime());
            refreshCartLayoutData();
        }*/
    }

    @Override
    public void onRefresh() {
        presenter.getMerchant(merchantCode, false);
    }

}
