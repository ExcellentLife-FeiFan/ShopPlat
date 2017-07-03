package com.ytxd.spp.util;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.ui.views.FakeAddImageView;
import com.ytxd.spp.ui.views.pop.SelectGoodStandDialog;

import java.util.List;

/**
 * Created by XY on 2017/6/16.
 */

public class ShoppingCartUtil {

    public static int getLocalCartGoodCount(String goodCode, String merchantCode) {
        int c = 0;
        QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
        List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            for (int i = 0; i < shoppingCartM.getShoppingCartM().goods.size(); i++) {
                if (shoppingCartM.getShoppingCartM().goods.get(i).getGoodM().getGoodsCode().equals(goodCode)) {
                    c = shoppingCartM.getShoppingCartM().goods.get(i).getCount();
                    break;
                }

            }
        }
        return c;

    }

    public static void refreshLocalCartGood(GoodM good, String merchantCode) {
        QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
        List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            for (int i = 0; i < shoppingCartM.getShoppingCartM().goods.size(); i++) {
                if (shoppingCartM.getShoppingCartM().goods.get(i).getGoodM().getGoodsCode().equals(good.getGoodsCode())) {
                    shoppingCartM.getShoppingCartM().goods.get(i).setGoodM(good);
                    App.liteOrm.update(shoppingCartM);
                    break;
                }

            }
        }

    }

    public static boolean goodMinusEvent(Context context, String merchantCode, GoodM goodM) {
        boolean canrefresh = false;
        if (CommonUtils.isDBInit(context)) {
            QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                    .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
            List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
            if (beans.size() > 0) {
                LocalShoppingCartM shoppingCartM = beans.get(0);
                shoppingCartM.getShoppingCartM().removeGood(goodM);
                int ch = App.liteOrm.update(shoppingCartM);
                LogUtils.e(ch + "");
                canrefresh = true;

            }
        }
        return canrefresh;

    }

    public static boolean goodAddEvent(Context context, MerchantM merchant, GoodM goodM) {
        boolean canrefresh = false;
        if (CommonUtils.isDBInit(context)) {
            QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                    .whereEquals(LocalShoppingCartM.CARTCODE, merchant.getSupermarketCode());
            List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
            if (beans.size() > 0) {
                LocalShoppingCartM shoppingCartM = beans.get(0);
                shoppingCartM.getShoppingCartM().addGood(goodM);
                int ch = App.liteOrm.update(shoppingCartM);
                LogUtils.e(ch + "");
            } else {
                ShoppingCartM shoppingCartM = new ShoppingCartM();
                shoppingCartM.setMerchantM(merchant);
                shoppingCartM.addGood(goodM);
                LocalShoppingCartM localShoppingCartM = new LocalShoppingCartM(merchant.getSupermarketCode(), shoppingCartM);
                long cc = App.liteOrm.save(localShoppingCartM);
                LogUtils.e(cc + "");
            }
            canrefresh = true;
        }
        return canrefresh;

    }

    public static boolean goodClearEvent(Context context, String merchantCode) {
        boolean canrefresh = false;
        if (CommonUtils.isDBInit(context)) {
            QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                    .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
            List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
            if (beans.size() > 0) {
                LocalShoppingCartM shoppingCartM = beans.get(0);
                shoppingCartM.getShoppingCartM().clearGoods();
                int ch = App.liteOrm.update(shoppingCartM);
                LogUtils.e(ch + "");
                canrefresh = true;
            }
        }
        return canrefresh;

    }

    public static void showGoodStandDialog(int type, String merchantCode, GoodM goodM, FragmentManager fragmentManager) {
        SelectGoodStandDialog standDialog = new SelectGoodStandDialog();
        Bundle data = new Bundle();
        data.putSerializable("data", goodM);
        data.putInt("type", type);
        data.putCharSequence("merchantCode", merchantCode);
        standDialog.setArguments(data);
        standDialog.show(fragmentManager, "SelectGoodStandDialog");
    }

    public static void addGoodAnimation(Context context, View view, ImageView shoppingCart, View top, final ViewGroup mainLayout) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCart.getLocationInWindow(cartLocation);
        top.getLocationInWindow(recycleLocation);

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

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(context);
        mainLayout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.ic_good_plus);
        fakeAddImageView.getLayoutParams().width = context.getResources().getDimensionPixelSize(R.dimen.dp24);
        fakeAddImageView.getLayoutParams().height = context.getResources().getDimensionPixelSize(R.dimen.dp24);
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

    public static boolean deleteCart(Context context, String merchantCode) {
        boolean isR = false;
        if (CommonUtils.isDBInit(context)) {
            QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                    .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
            List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
            if (beans.size() > 0) {
                LocalShoppingCartM shoppingCartM = beans.get(0);
                App.liteOrm.delete(shoppingCartM);
                isR = true;
            }
        }
        return isR;

    }

}
