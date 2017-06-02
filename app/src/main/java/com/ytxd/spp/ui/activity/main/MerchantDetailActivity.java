package com.ytxd.spp.ui.activity.main;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
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
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.flyco.systembar.SystemBarHelper;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.MerchantGoodAddEvent;
import com.ytxd.spp.model.MerchantGoodM;
import com.ytxd.spp.model.MerhchantGoodCategoryM;
import com.ytxd.spp.ui.adapter.MerchantCategoryA;
import com.ytxd.spp.ui.adapter.MerchantGoodA;
import com.ytxd.spp.ui.views.FakeAddImageView;
import com.ytxd.spp.ui.views.SimpleDividerDecoration;
import com.ytxd.spp.ui.views.pop.MerchantCartListPopWindow;
import com.ytxd.spp.util.PointFTypeEvaluator;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MerchantDetailActivity extends BaseActivity {

    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_good)
    RecyclerView rvGood;
    MerchantGoodA goodA;
    MerchantCategoryA categoryA;
    StickyHeaderLayoutManager goodLM;
    LinearLayoutManager categoryLM;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.main_layout)
    RelativeLayout mainLayout;
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout shopping_cart_bottom;


    /*    @BindView(R.id.rl_cart_list)
        RelativeLayout rlCartList;*/
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_detail);
        ButterKnife.bind(this);
        toolbar.setTitle("");
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


        goodA = new MerchantGoodA();
        goodLM = new StickyHeaderLayoutManager();
        rvGood.setLayoutManager(goodLM);
        rvGood.addItemDecoration(new SimpleDividerDecoration(this, R.color.line_gray));
        rvGood.setAdapter(goodA);


        List<MerchantGoodM> goods = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            MerchantGoodM good = new MerchantGoodM();
            good.setName("产品" + i);
            goods.add(good);
        }
        List<MerhchantGoodCategoryM> categoryMS = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MerhchantGoodCategoryM categoryM = new MerhchantGoodCategoryM();
            categoryM.setGoods(goods);
            categoryM.setName("分类" + i);
            categoryMS.add(categoryM);
        }
        setSectionAdapterPosition(categoryMS);

        goodA.addAll(categoryMS);
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


        categoryA = new MerchantCategoryA(categoryMS);
        categoryLM = new LinearLayoutManager(this);
        rvCategory.setLayoutManager(categoryLM);
        rvCategory.addItemDecoration(new SimpleDividerDecoration(this, R.color.line_gray));
        rvCategory.setAdapter(categoryA);
        rvCategory.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                categoryA.setPositionS(position);
                MerhchantGoodCategoryM setion = goodA.getSection(position);
                if (setion.getAdapterP() >= 0) {
                    goodLM.scrollToPosition(setion.getAdapterP());
                }
            }
        });

    }

    private void setSectionAdapterPosition(List<MerhchantGoodCategoryM> categories) {
        int count = 0;
        for (int i = 0; i < categories.size(); i++) {
            MerhchantGoodCategoryM one = categories.get(i);
            one.setAdapterP(count + 2 * i);
            count = count + one.getGoods().size();
        }

    }


    public void onEvent(MerchantGoodAddEvent event) {
        if (null != event.getView()) {
            addGoodAnimation(event.getView());
        }
    }

    private void addGoodAnimation(View view) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCart.getLocationInWindow(cartLocation);
        toolbar.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1] + 90
        ;
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(this);
        mainLayout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.ic_good_plus);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.dp28);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dp28);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                mainLayout.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCart, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCart, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(800);
        animatorSet.start();
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
                break;

            case R.id.more:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    MerchantCartListPopWindow merchantCartListPopWindow;

    @OnClick({R.id.btn_ok, R.id.shopping_cart_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                break;
            case R.id.shopping_cart_layout:
                if (null == merchantCartListPopWindow) {
                    merchantCartListPopWindow = new MerchantCartListPopWindow(this);
                }
                if(!merchantCartListPopWindow.isShowing()){
                    merchantCartListPopWindow.showAtLocation(mainLayout, Gravity.BOTTOM,0,0);
                }else{
                    merchantCartListPopWindow.dismiss();
                }
//                toggleCartMenu();
                break;
        }
    }

    /*private void toggleCartMenu(){
        if (rlCartList.getVisibility() == View.VISIBLE) {
            exlayoutCartList.hide();
            exlayoutCartList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    rlCartList.setVisibility(View.GONE);
                }
            }, 200);

        } else {
            rlCartList.setVisibility(View.VISIBLE);
            exlayoutCartList.postDelayed(new Runnable() {
                @Override
                public void run() {
                    exlayoutCartList.show();
                }
            }, 200);
        }
    }*/
}
