package com.ytxd.spp.ui.activity.main;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.flyco.systembar.SystemBarHelper;
import com.mcxtzhang.lib.AnimShopButton;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.ui.adapter.GoodCommentLV;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.ui.views.pop.SelectGoodStandDialog;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.ytxd.spp.R.id.rl_add_btn;

public class GoodDetailActivity extends BaseActivity {

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
    GoodM goodM;


    @BindView(R.id.shopping_cart_total_tv)
    TextView shoppingCartTotalTv;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.shopping_cart_total_num)
    TextView shoppingCartTotalNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_detail);
        ButterKnife.bind(this);
        goodM = (GoodM) getIntent().getSerializableExtra("data");
        CommonUtils.setText(tvGoodName, goodM.getGoodsTitle());
        CommonUtils.setText(tvMonthSales, goodM.getSaleNumber() + "");
        ImageLoadUtil.setImageNP(goodM.getLogoPaths(), ivGood, this);

        if (null != goodM.getGoods() && goodM.getGoods().size() > 0) {
            ll_no_stand.setVisibility(View.GONE);
            ll_have_stand.setVisibility(View.VISIBLE);
            rlAddBtn.setVisibility(View.GONE);
            tvSelectStand.setVisibility(View.VISIBLE);
        } else {
            CommonUtils.setText(tvPrice, goodM.getXPrice());
            CommonUtils.setText(tvOriginP, goodM.getYPrice());
            ll_no_stand.setVisibility(View.VISIBLE);
            ll_have_stand.setVisibility(View.GONE);
            rlAddBtn.setVisibility(View.VISIBLE);
            tvSelectStand.setVisibility(View.GONE);
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
    }

    @OnClick({R.id.tv_select_stand, R.id.ll_comment_more, R.id.tv_num_comment})
    public void onViewClicked2(View view) {
        switch (view.getId()) {
            case R.id.tv_select_stand:
                if (null != goodM) {
                    SelectGoodStandDialog standDialog = new SelectGoodStandDialog();
                    Bundle data = new Bundle();
                    data.putSerializable("data", goodM);
                    standDialog.setArguments(data);
                    standDialog.show(getFragmentManager(), "SelectGoodStandDialog");
                }
                break;
            case R.id.ll_comment_more:
            case R.id.tv_num_comment:
                startActivity(GoodCommentsActivity.class);
                break;
        }
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
    }

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
