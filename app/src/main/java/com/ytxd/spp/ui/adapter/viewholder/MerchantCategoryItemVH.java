package com.ytxd.spp.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ytxd.spp.R;

/**
 * Created by XY on 2016/11/4.
 */

public class MerchantCategoryItemVH extends BaseViewHolder {
    public View rootView;
    public TextView tv_name;


    public MerchantCategoryItemVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
    }

}
