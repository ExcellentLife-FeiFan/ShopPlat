package com.ytxd.spp.ui.views.pop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytxd.spp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by apple on 2017/6/2.
 */

public class MerchantCartListDialog extends Dialog {


    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.shopping_cart_total_tv)
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
    @BindView(R.id.shopping_cart_total_num)
    TextView shoppingCartTotalNum;
    @BindView(R.id.rl_cart_btn)
    RelativeLayout rlCartBtn;


    public MerchantCartListDialog(Context context) {
        super(context, R.style.cartdialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_merchant_cart_list);
        ButterKnife.bind(this);

    }

    @Override
    public void show() {
        super.show();
        animationShow(500);
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


    @OnClick({R.id.tv_clear, R.id.btn_ok, R.id.shopping_cart_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                break;
            case R.id.btn_ok:
                break;
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
