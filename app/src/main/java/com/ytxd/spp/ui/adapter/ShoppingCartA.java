package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;


public class ShoppingCartA extends BaseQuickAdapter<String, ShoppingCartItemVH> {
    public ShoppingCartA(List<String> data) {
        super(R.layout.item_shopping_cart, data);
    }

    protected ShoppingCartItemVH createBaseViewHolder(View view) {
        return new ShoppingCartItemVH(view);
    }


    @Override
    protected void convert(ShoppingCartItemVH helper, String item) {
        helper.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        helper.tvMerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        ShoppingCartGoodsLV goodsLV = new ShoppingCartGoodsLV(CommonUtils.getSampleList(4), mContext);
        helper.lv.setAdapter(goodsLV);


    }

}
