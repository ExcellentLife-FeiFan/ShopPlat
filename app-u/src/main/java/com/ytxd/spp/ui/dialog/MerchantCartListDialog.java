package com.ytxd.spp.ui.dialog;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ytxd.spp.R;
import com.ytxd.spp.event.CartListClearRefreshEvent;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.ui.activity.order.EnsureOrderActivity;
import com.ytxd.spp.ui.adapter.CartListDialogLV;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by apple on 2017/6/2.
 */

public class MerchantCartListDialog extends Dialog {


    @BindView(R.id.tv_clear)
    TextView tvClear;
    /*    @BindView(R.id.tv_empty)
        TextView tvEmpty;*/
    @BindView(R.id.rl_content)
    RelativeLayout rl_content;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.tv_total_p)
    TextView shoppingCartTotalTv;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout shoppingCartBottom;
    @BindView(R.id.rl_cast_btn)
    RelativeLayout rlCastBtn;
    @BindView(R.id.shopping_cart)
    ImageView shoppingCart;
    @BindView(R.id.shopping_cart_layout)
    FrameLayout shoppingCartLayout;
    @BindView(R.id.tv_total_c)
    TextView shoppingCartTotalNum;
    @BindView(R.id.rl_cart_btn)
    RelativeLayout rlCartBtn;
    CartListDialogLV goodListA;
    String merchantCode;
    MerchantM merchantM;
    int type;

    private Context context;


    public MerchantCartListDialog(Context context, MerchantM merchant, int type) {
        super(context, R.style.cartdialog);
        this.merchantM = merchant;
        merchantCode = merchantM.getSupermarketCode();
        this.type = type;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_merchant_cart_list);
        ButterKnife.bind(this);
        goodListA = new CartListDialogLV(new ArrayList<ShoppingCartM.Goods>(), getContext(), type);
        lv.setAdapter(goodListA);
//        CommonUtils.setEmptyViewForSLV(getContext(),"购物车暂时没有商品",rl_content,lv);
    }

    @Override
    public void show() {
        super.show();
        setData();
        animationShow(500);
    }

    public void setData() {
        List<LocalShoppingCartM> beans = ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
        btnOk.setText("选好了");
        btnOk.setEnabled(true);
        if (beans.size() > 0) {
            LocalShoppingCartM shoppingCartM = beans.get(0);
            goodListA.addItems(shoppingCartM.getShoppingCartM().getGoods(), true);
            int count = shoppingCartM.getShoppingCartM().getGoodsCounts();
            if (count != 0) {
                shoppingCartTotalNum.setText(count + "");
                shoppingCartTotalTv.setText("共计¥" + shoppingCartM.getShoppingCartM().getPirceTotal());
                float qs = Float.valueOf(merchantM.getQSPrice());
                float p = Float.valueOf(shoppingCartM.getShoppingCartM().getPirceTotal());
                if (p >= qs) {
                    btnOk.setEnabled(true);
                } else {
                    btnOk.setText("满" + merchantM.getQSPrice() + "送");
                    btnOk.setEnabled(false);
                }
                String canBuyS = ShoppingCartUtil.canBuy(merchantM);
                if (!canBuyS.equals("OK")) {
                    btnOk.setEnabled(false);
                    btnOk.setText(canBuyS);
                }
                return;
            }
        }
        goodListA.clearItems();
        btnOk.setEnabled(false);
        shoppingCartTotalNum.setText("0");
        shoppingCartTotalTv.setText(CommonUtils.getString(R.string.none_goods));
        String canBuyS = ShoppingCartUtil.canBuy(merchantM);
        if (!canBuyS.equals("OK")) {
            btnOk.setEnabled(false);
            btnOk.setText(canBuyS);
        }
    }

    @Override
    public void dismiss() {
        animationHide(500);
    }


    private void animationShow(int mDuration) {


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearlayout, "translationY", 50, 0).setDuration(mDuration)
                , ObjectAnimator.ofFloat(linearlayout, "scaleY", 0, 1).setDuration(mDuration)
        );
        animatorSet.start();
    }

    private void animationHide(int mDuration) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                ObjectAnimator.ofFloat(linearlayout, "translationY", 0, 50).setDuration(mDuration)
                , ObjectAnimator.ofFloat(linearlayout, "scaleY", 1, 0).setDuration(mDuration)
        );
        animatorSet.start();

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                MerchantCartListDialog.super.dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


    @OnClick({R.id.v, R.id.tv_clear, R.id.btn_ok, R.id.shopping_cart_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                if (goodListA.getCount() > 0) {
                    DialogUtils.showDialog(getContext(), "提示", "确定清空购物车吗？", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which.name().equals(DialogAction.POSITIVE.name())) {
                                if (ShoppingCartUtil.goodClearEvent(getContext(), merchantCode)) {
                                    ToastUtil.showToastShort(context, "清除成功");
                                    setData();
                                    EventBus.getDefault().post(new CartListClearRefreshEvent());
                                }
                            }
                        }
                    });
                } else {
                    ToastUtil.showToastShort(context, "购物车没有商品");
                }
                break;
            case R.id.btn_ok:
                if (CommonUtils.isLogined((Activity) context, true)) {
                    Intent intent = new Intent(getContext(), EnsureOrderActivity.class);
                    intent.putExtra("merchant", merchantM);
                    getContext().startActivity(intent);
                }
                break;
            case R.id.v:
            case R.id.shopping_cart_layout:
                dismiss();
                break;
        }
    }



   /* public void clear(){
        shopCart.clear();
        showTotalPrice();
        if(shopCart.getShoppingAccount()==0){
            this.dismiss();
        }
    }*/
}
