package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.ui.adapter.viewholder.HomeMerchantItemVH;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;


public class HomeMerchantA extends BaseQuickAdapter<MerchantM, HomeMerchantItemVH> {
    public HomeMerchantA(List<MerchantM> data) {
        super(R.layout.item_home_merchant, data);
    }

    protected HomeMerchantItemVH createBaseViewHolder(View view) {
        return new HomeMerchantItemVH(view);
    }


    @Override
    protected void convert(HomeMerchantItemVH helper, MerchantM item) {
        helper.tvName.setText(item.getName());
        helper.tvQisongP.setText("¥"+item.getQSPrice()+"起送");
        helper.tvDistriP.setText("配送费¥"+item.getQSPrice());
        ImageLoadUtil.setImageNP(item.getLogoUrl(),helper.icon,mContext);
    }

}
