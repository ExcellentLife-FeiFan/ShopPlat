package com.ytxd.spp.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;
import com.ytxd.spp.R;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by XY on 2016/11/4.
 */

public class HomeMerchantItemVH extends BaseViewHolder {
    public View rootView;
    public RoundedImageView icon;
    public TextView tvName;
    public MaterialRatingBar rb;
    public TextView tvRbScore;
    public TextView tvMonthSales;
    public TextView tvQisongP;
    public TextView tvDistriP;
    public TextView tvPerPeoP;
    public TextView tvDistance;
    public TextView tvDistriTime;


    public HomeMerchantItemVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.icon = (RoundedImageView) rootView.findViewById(R.id.icon);
        this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        this.rb = (MaterialRatingBar) rootView.findViewById(R.id.rb);
        this.tvRbScore = (TextView) rootView.findViewById(R.id.tv_rb_score);
        this.tvMonthSales = (TextView) rootView.findViewById(R.id.tv_month_sales);
        this.tvQisongP = (TextView) rootView.findViewById(R.id.tv_qisong_p);
        this.tvDistriP = (TextView) rootView.findViewById(R.id.tv_distri_p);
        this.tvPerPeoP = (TextView) rootView.findViewById(R.id.tv_per_peo_p);
        this.tvDistance = (TextView) rootView.findViewById(R.id.tv_distance);
        this.tvDistriTime = (TextView) rootView.findViewById(R.id.tv_distri_time);
    }

}
