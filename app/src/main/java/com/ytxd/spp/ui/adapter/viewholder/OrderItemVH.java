package com.ytxd.spp.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ytxd.spp.R;

/**
 * Created by XY on 2016/11/4.
 */

public class OrderItemVH extends BaseViewHolder {
    public View rootView;
    public ImageView civ;
    public TextView tvMerName;
    public TextView tvState;
    public TextView tvTime;
    public TextView tvGoodsDesr;
    public TextView tvTotalPrice;
    public TextView tvBuiedNum;
    public TextView tvAgain;
    public TextView tvEvaluate;


    public OrderItemVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.civ = (ImageView) rootView.findViewById(R.id.civ);
        this.tvMerName = (TextView) rootView.findViewById(R.id.tv_mer_name);
        this.tvState = (TextView) rootView.findViewById(R.id.tv_state);
        this.tvTime = (TextView) rootView.findViewById(R.id.tv_time);
        this.tvGoodsDesr = (TextView) rootView.findViewById(R.id.tv_goods_desr);
        this.tvTotalPrice = (TextView) rootView.findViewById(R.id.tv_total_price);
        this.tvBuiedNum = (TextView) rootView.findViewById(R.id.tv_buied_num);
        this.tvAgain = (TextView) rootView.findViewById(R.id.tv_again);
        this.tvEvaluate = (TextView) rootView.findViewById(R.id.tv_evaluate);

    }

}
