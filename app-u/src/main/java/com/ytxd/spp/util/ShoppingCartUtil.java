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
import com.ytxd.spp.ui.dialog.SelectGoodStandDialog;
import com.ytxd.spp.ui.views.FakeAddImageView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by XY on 2017/6/16.
 */

public class ShoppingCartUtil {

    /**
     *
     * */
    public static int getLocalCartGoodCount(String goodCode, String merchantCode) {
        int c = 0;
        List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
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

    public static void updateLocalCartGood(GoodM good, String merchantCode) {
        List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
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

    public static void deleteLocalCartGood(GoodM good, String merchantCode) {
        List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            for (int i = 0; i < shoppingCartM.getShoppingCartM().goods.size(); i++) {
                if (shoppingCartM.getShoppingCartM().goods.get(i).getGoodM().getGoodsCode().equals(good.getGoodsCode())) {
                    shoppingCartM.getShoppingCartM().goods.remove(i);
                    App.liteOrm.update(shoppingCartM);
                    break;
                }

            }
        }

    }

    /**
     * 添加单个商品到本地购物车
     */
    public static boolean goodMinusEvent(Context context, String merchantCode, GoodM goodM) {
        boolean canrefresh = false;
        if (CommonUtils.isDBInit(context)) {
            List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
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

    /**
     * 减去单个商品从本地购物车
     */
    public static boolean goodAddEvent(Context context, MerchantM merchant, GoodM goodM) {
        boolean canrefresh = false;
        if (CommonUtils.isDBInit(context)) {
            List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchant.getSupermarketCode());
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

    public static boolean addGoods(Context context, MerchantM merchant, GoodM goodM, int count) {
        boolean canrefresh = false;
        if (CommonUtils.isDBInit(context)) {
            List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchant.getSupermarketCode());
            if (beans.size() > 0) {
                LocalShoppingCartM shoppingCartM = beans.get(0);
                for (int i = 0; i < count; i++) {
                    shoppingCartM.getShoppingCartM().addGood(goodM);
                }
                int ch = App.liteOrm.update(shoppingCartM);
                LogUtils.e(ch + "");
            } else {
                ShoppingCartM shoppingCartM = new ShoppingCartM();
                shoppingCartM.setMerchantM(merchant);
                for (int i = 0; i < count; i++) {
                    shoppingCartM.addGood(goodM);
                }
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
            List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
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
            List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
            if (beans.size() > 0) {
                LocalShoppingCartM shoppingCartM = beans.get(0);
                App.liteOrm.delete(shoppingCartM);
                isR = true;
            }
        }
        return isR;

    }

    public static String canBuy(MerchantM merchantM) {
        if (!CommonUtils.getBoolean(merchantM.getShopsOpen())) {
            return "未营业";
        }
      /*  boolean isRest;
        String b = CommonUtils.getFormatTimeString(merchantM.getBusinessBeginTime());
        String[] bs = b.split(":");
        float bt = Float.valueOf(bs[0]) * 60f + Float.valueOf(bs[1]);
        String e = CommonUtils.getFormatTimeString(merchantM.getBusinessEndTime());
        String[] es = e.split(":");
        float et = Float.valueOf(es[0]) * 60f + Float.valueOf(es[1]);
        String n = AbDateUtil.getCurrentDate(AbDateUtil.dateFormatHM);
        String[] ns = n.split(":");
        float nt = Float.valueOf(ns[0]) * 60f + Float.valueOf(ns[1]);
        if (nt >= bt && nt <= et) {
            isRest = false;
        } else {
            isRest = true;
        }
        if (isRest) {
            return "正在休息";
        }*/

        return "OK";
    }

    public static List<LocalShoppingCartM> getLocalShoppingCartMs(String merchantCode) {
        QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
        List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
        return beans;
    }


    //根据在线获取并临时保存到本地数据库的商品列表来剔除本地购物车中商店不存在（删除）的商品
    public static boolean refreshLocalCartGoodByOnlineGot(String merchantCode) {
        List<LocalShoppingCartM> beans = getLocalShoppingCartMs(merchantCode);
        int count = 0;
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            List<ShoppingCartM.Goods> goods = shoppingCartM.getShoppingCartM().getGoods();

            //遍历查询删除
            Iterator<ShoppingCartM.Goods> iterator = goods.iterator();
            while (iterator.hasNext()) {
                ShoppingCartM.Goods good = iterator.next();
                QueryBuilder queryBuilder = new QueryBuilder(GoodM.class)
                        .whereEquals("GoodsCode", good.getGoodM().getGoodsCode());
                List<GoodM> gs = App.liteOrm.query(queryBuilder);
                if (gs.size() <= 0) {
                    iterator.remove();//移除当前
                    count++;
                }
            }
            if (count > 0) {
                App.liteOrm.update(shoppingCartM);
                return true;
            }
        }
        return false;
    }

    public static boolean isPriceChanged(GoodM goodM1, GoodM goodM2) {
        float lg = Float.valueOf(goodM1.getYPrice());
        float ln = Float.valueOf(goodM2.getYPrice());
        if (lg != ln) {
            return true;
        }
        return false;
    }
}
