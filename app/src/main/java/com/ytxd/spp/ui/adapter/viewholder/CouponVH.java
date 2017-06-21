package com.ytxd.spp.ui.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ytxd.spp.R;

/**
 * Created by XY on 2016/11/4.
 */

public class CouponVH extends BaseViewHolder {
    public View rootView;
    public TextView tvPrice;
    public TextView tvTitle;
    public TextView tvDesrc;
    public TextView tvUsedTime;
    public ImageView ivClosed;
    public LinearLayout llBg;


    public CouponVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.tvPrice = (TextView) rootView.findViewById(R.id.tv_price);
        this.tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
        this.tvDesrc = (TextView) rootView.findViewById(R.id.tv_desrc);
        this.tvUsedTime = (TextView) rootView.findViewById(R.id.tv_used_time);
        this.ivClosed= (ImageView) rootView.findViewById(R.id.iv_close);
        this.llBg= (LinearLayout) rootView.findViewById(R.id.ll_bg);
    }

}
