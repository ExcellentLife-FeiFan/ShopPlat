package com.ytxd.spp.ui.activity.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.flyco.systembar.SystemBarHelper;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.BaseActivity2;
import com.ytxd.spp.event.CartListClearRefreshEvent;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.ui.activity.order.EnsureOrderActivity;
import com.ytxd.spp.ui.adapter.GoodCommentLV;
import com.ytxd.spp.ui.views.AnimShopButton;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.ui.dialog.MerchantCartListDialog;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.ShoppingCartUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static com.ytxd.spp.R.id.rl_add_btn;

public class GoodDetailActivity extends BaseActivity2 {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.tv_good_name)
    TextView tvGoodName;
    @BindView(R.id.tv_month_sales)
    TextView tvMonthSales;
    @BindView(R.id.iv_good)
    ImageView ivGood;
    @BindView(R.id.tv_favor_persent)
    TextView tvFavorPersent;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_origin_p)
    TextView tvOriginP;
    @BindView(R.id.ll_no_stand)
    LinearLayout ll_no_stand;
    @BindView(R.id.ll_have_stand)
    LinearLayout ll_have_stand;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.btnAdd)
    AnimShopButton btnAdd;
    @BindView(rl_add_btn)
    RelativeLayout rlAddBtn;
    @BindView(R.id.tv_select_stand)
    TextView tvSelectStand;
    @BindView(R.id.tv_good_comment_persent)
    TextView tvGoodCommentPersent;
    @BindView(R.id.tv_num_comment)
    TextView tvNumComment;
    @BindView(R.id.ll_comment_more)
    LinearLayout llCommentMore;
    GoodCommentLV mAdapter;
    @BindView(R.id.lv_comment)
    InListView lvComment;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.mainlayout)
    RelativeLayout mainlayout;
    @BindView(R.id.tv_total_p)
    TextView tvTotalP;
    @BindView(R.id.tv_total_c)
    TextView tvTotalNum;

    String merchantCode;
    GoodM goodM;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private MerchantM merchantM;
    private MerchantCartListDialog cartListDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        SystemBarHelper.immersiveStatusBar(this, 0f);
        goodM = (GoodM) getIntent().getSerializableExtra("data");
        merchantCode = getIntent().getStringExtra("merchantCode");
        merchantM = (MerchantM) getIntent().getSerializableExtra("merchant");
        CommonUtils.setText(tvGoodName, goodM.getGoodsTitle());
        CommonUtils.setText(tvMonthSales, goodM.getSaleNumber() + "");
        ImageLoadUtil.setImageNP(goodM.getLogoPaths(), ivGood, this);
        refreshCartLayoutData();
        if (null != goodM.getGoods() && goodM.getGoods().size() > 0) {
            ll_no_stand.setVisibility(View.GONE);
            ll_have_stand.setVisibility(View.VISIBLE);
            rlAddBtn.setVisibility(View.INVISIBLE);
            tvSelectStand.setVisibility(View.VISIBLE);
        } else {
            CommonUtils.setText(tvPrice, goodM.getXPrice());
            CommonUtils.setText(tvOriginP, goodM.getYPrice());
            ll_no_stand.setVisibility(View.VISIBLE);
            ll_have_stand.setVisibility(View.GONE);
            rlAddBtn.setVisibility(View.VISIBLE);
            tvSelectStand.setVisibility(View.INVISIBLE);
        }

        toolbar.setTitle(goodM.getGoodsTitle());
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        SystemBarHelper.immersiveStatusBar(this, 0.4f);
        SystemBarHelper.setHeightAndPadding(this, toolbar);
        mAdapter = new GoodCommentLV(CommonUtils.getSampleList(4), this);
        lvComment.setAdapter(mAdapter);
        btnAdd.setCount(ShoppingCartUtil.getLocalCartGoodCount(goodM.getGoodsCode(), merchantCode));
        btnAdd.setOnAddDelListener(new AnimShopButton.IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                EventBus.getDefault().post(new GoodAddEvent(ivPlus, goodM, 2));
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                EventBus.getDefault().post(new GoodMinusEvent(goodM, 2));
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
    }

    private void showCart() {
        if (null == cartListDialog) {
            cartListDialog = new MerchantCartListDialog(this, merchantM, 2);
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

    private void refreshCartLayoutData() {
        QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
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
        btnAdd.setCount(ShoppingCartUtil.getLocalCartGoodCount(goodM.getGoodsCode(), merchantCode));
        btnAdd.invalidate();
    }

    public void onEvent(GoodAddEvent event) {
        if (null != event.getView()) {
            ShoppingCartUtil.addGoodAnimation(this, event.getView(), shoppingCart, toolbar, mainlayout);
        }
        if (event.type == 2) {
            if (ShoppingCartUtil.goodAddEvent(this, merchantM, event.getGoodM())) {
                refreshCartLayoutData();
            }
        } else {
            refreshCartLayoutData();
        }

    }

    public void onEvent(GoodMinusEvent event) {
        if (event.type == 2) {
            if (ShoppingCartUtil.goodMinusEvent(this, merchantCode, event.getGoodM())) {
                refreshCartLayoutData();
            }
        } else {
            refreshCartLayoutData();
        }

    }

    public void onEvent(CartListClearRefreshEvent event) {
        refreshCartLayoutData();
    }


    @OnClick({R.id.tv_select_stand, R.id.ll_comment_more, R.id.tv_num_comment, R.id.shopping_cart_layout})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.tv_select_stand:
                if (null != goodM) {
                    ShoppingCartUtil.showGoodStandDialog(2, merchantCode, goodM, getFragmentManager());
                }
                break;
            case R.id.ll_comment_more:
            case R.id.tv_num_comment:
                startActivity(GoodCommentsActivity.class);
                break;
            case R.id.shopping_cart_layout:
                showCart();
                break;
        }
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        if (!tvTotalNum.getText().toString().equals("0")) {
            startActivity(EnsureOrderActivity.class, "merchantCode", merchantCode);
        }
    }





















/*        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, CommonUtils.getSampleList(5))
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_banner_dot_ns, R.drawable.ic_banner_dot_s})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(3000);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        convenientBanner.setManualPageable(false);//设置不能手动影响*/
/*
    public class LocalImageHolderView implements Holder<String> {

        @Override
        public View createView(Context context) {
            View imgV = getLayoutInflater().inflate(R.layout.item_banner_image, null);
            ImageView imageView = (ImageView) imgV.findViewById(R.id.iv);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(R.mipmap.img_food);
            return imgV;
        }

        @Override
        public void UpdateUI(Context context, final int position, String data) {


        }


    }*/
}
