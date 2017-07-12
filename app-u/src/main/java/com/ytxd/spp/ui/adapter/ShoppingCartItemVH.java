package com.ytxd.spp.ui.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ytxd.spp.R;
import com.ytxd.spp.ui.views.InListView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XY on 2016/11/4.
 */

public class ShoppingCartItemVH extends BaseViewHolder {
    public View rootView;
    public CircleImageView civ;
    public TextView tvMerName;
    public ImageView ivDelete;
    public LinearLayout llShop;
    public InListView lv;
    public TextView tvDiscountPrice;
    public TextView tvTotalPrice;
    public Button btnPay;

    public ShoppingCartItemVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.civ = (CircleImageView) rootView.findViewById(R.id.civ);
        this.tvMerName = (TextView) rootView.findViewById(R.id.tv_mer_name);
        this.ivDelete = (ImageView) rootView.findViewById(R.id.iv_delete);
        this.llShop = (LinearLayout) rootView.findViewById(R.id.ll_shop);
        this.lv = (InListView) rootView.findViewById(R.id.lv);
        this.tvDiscountPrice = (TextView) rootView.findViewById(R.id.tv_discount_price);
        this.tvTotalPrice = (TextView) rootView.findViewById(R.id.tv_total_price);
        this.btnPay = (Button) rootView.findViewById(R.id.btn_pay);


    }

}
