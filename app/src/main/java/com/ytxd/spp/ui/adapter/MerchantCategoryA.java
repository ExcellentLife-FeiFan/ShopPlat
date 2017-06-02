package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.MerhchantGoodCategoryM;
import com.ytxd.spp.ui.adapter.viewholder.MerchantCategoryItemVH;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;


public class MerchantCategoryA extends BaseQuickAdapter<MerhchantGoodCategoryM, MerchantCategoryItemVH> {
    public int positionS = 0;

    public MerchantCategoryA(List<MerhchantGoodCategoryM> data) {
        super(R.layout.item_merchant_category, data);
    }

    protected MerchantCategoryItemVH createBaseViewHolder(View view) {
        return new MerchantCategoryItemVH(view);
    }


    @Override
    protected void convert(MerchantCategoryItemVH helper, MerhchantGoodCategoryM item) {
        if (positionS == helper.getAdapterPosition()) {
            CommonUtils.setBackgroundDrawable(helper.rootView, R.color.white);
            CommonUtils.setTextColor(helper.tv_name,R.color.colorPrimary);
        } else {
            CommonUtils.setBackgroundDrawable(helper.rootView, R.color.bg);
            CommonUtils.setTextColor(helper.tv_name,R.color.black_overlay2);
        }
        helper.tv_name.setText(item.getName());
    }

    public int getPositionS() {
        return positionS;
    }

    public void setPositionS(int positionS) {
        this.positionS = positionS;
        notifyDataSetChanged();
    }
}
