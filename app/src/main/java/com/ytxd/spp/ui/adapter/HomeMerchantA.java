package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.ui.adapter.viewholder.HomeMerchantItemVH;

import java.util.List;



public class HomeMerchantA extends BaseQuickAdapter<String, HomeMerchantItemVH> {
    public HomeMerchantA(List<String> data) {
        super(R.layout.item_home_merchant, data);
    }

    protected HomeMerchantItemVH createBaseViewHolder(View view) {
        return new HomeMerchantItemVH(view);
    }


    @Override
    protected void convert(HomeMerchantItemVH helper, String item) {


    }

}
