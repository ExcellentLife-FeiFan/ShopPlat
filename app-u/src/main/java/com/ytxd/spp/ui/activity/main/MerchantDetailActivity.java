package com.ytxd.spp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import com.makeramen.roundedimageview.RoundedImageView;
import com.qiushui.blurredview.BlurredView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.BaseActivity2;
import com.ytxd.spp.event.CartListClearRefreshEvent;
import com.ytxd.spp.event.CartListDialogShowEvent;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.event.MerchantSelectGoodStandEvent;
import com.ytxd.spp.event.RefreshGoodRVEvent;
import com.ytxd.spp.event.RefreshLoginEvent;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.presenter.MerchantPresenter;
import com.ytxd.spp.ui.activity.order.EnsureOrderActivity;
import com.ytxd.spp.ui.activity.order.ShoppingCartActivity;
import com.ytxd.spp.ui.dialog.MerchantCartListDialog;
import com.ytxd.spp.ui.fm.merchant.MerchantEvaluateFM;
import com.ytxd.spp.ui.fm.merchant.MerchantGoodFM;
import com.ytxd.spp.ui.views.MyExpandableLayout;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.view.IMerchantView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class MerchantDetailActivity extends BaseActivity2<MerchantPresenter> implements IMerchantView {

    /*    @BindView(R.id.iv_bg)
        ImageView ivBg;*/
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
    String[] titles = {"商品", "评价"};
    @BindView(R.id.blurredview)
    BlurredView blurredview;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;


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
        initViews();

    }

    private void initViews() {

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(2);
        tab.setViewPager(vp, titles);
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
//            ImageLoadUtil.setImageNP(merchantM.getHJUrl(), ivBg, this);
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
//            ImageLoadUtil.setImageNP(merchantM.getLogoUrl(), ivBg, this);
        }
        if (null != merchantM.getManJian()) {
            setActiviesData(merchantM.getManJian());
        } else {
            presenter.getManjian(merchantCode);
        }
        tvQisongP.setText("¥" + merchantM.getQSPrice() + "起送");
        tvDistriP.setText("配送费¥" + merchantM.getPSPrice());
        CommonUtils.setText(tvNotice,merchantM.getNotice());
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

    public void refreshCartLayoutData() {
        List<LocalShoppingCartM> beans =  ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
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
                    btnOk.setText("满"+merchantM.getQSPrice()+"送");
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

        String canBuyS=ShoppingCartUtil.canBuy(merchantM);
        if(!canBuyS.equals("OK")){
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


    @OnClick({R.id.btn_ok, R.id.shopping_cart_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                if (!tvTotalNum.getText().toString().equals("0")) {
                    if(CommonUtils.isLogined(this,true)){
                        startActivity(EnsureOrderActivity.class, "merchantCode", merchantCode);
                    }
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
            rlCart.setVisibility(View.INVISIBLE);
        } else {
            rlCart.setVisibility(View.VISIBLE);
        }

    }

    public void onEvent(CartListClearRefreshEvent event) {
        refreshCartLayoutData();
        EventBus.getDefault().post(new RefreshGoodRVEvent());
    }

    @Override
    public void init() {

    }


    @Override
    public void lodeManjianSuccess(List<MerchantM.ManJianBean> items) {
        merchantM.setManJian(items);
        setActiviesData(items);
    }

    public void onEvent(MerchantSelectGoodStandEvent event) {
        ShoppingCartUtil.showGoodStandDialog(1, merchantCode, event.goodM, getFragmentManager());
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
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
                data.putSerializable("data", merchantM);
                data.putString("orderCode", orderCode);
                fragment1.setArguments(data);
                return fragment1;
            } else if (position == 1) {
                MerchantEvaluateFM fragment2 = new MerchantEvaluateFM();
                Bundle data = new Bundle();
                data.putSerializable("data", merchantM);
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshCartLayoutData();
      /*  if (null != goodA) {
            goodA.notifyDataSetChanged();
        }*/
    }

    public void showBottomCart() {
        if(rlCart.getVisibility()==View.VISIBLE){
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
        if(rlCart.getVisibility()==View.GONE){
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
        if(resultCode==1001){
            if(data.getBooleanExtra("payback",false)){
                List<LocalShoppingCartM> beans =  ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
                ShoppingCartUtil.deleteCart(this,merchantCode);
                App.initDataBase(this);
                ShoppingCartUtil.deleteCart(this,merchantCode);
                App.liteOrm.save(beans);
                startActivity(EnsureOrderActivity.class, "merchantCode", merchantCode);
                EventBus.getDefault().post(new RefreshLoginEvent());
            }
        }
    }
}
