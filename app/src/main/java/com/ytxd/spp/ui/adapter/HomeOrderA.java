package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.ui.adapter.viewholder.OrderItemVH;

import java.util.List;


public class HomeOrderA extends BaseQuickAdapter<String, OrderItemVH> {
    public HomeOrderA(List<String> data) {
        super(R.layout.item_order, data);
    }

    protected OrderItemVH createBaseViewHolder(View view) {
        return new OrderItemVH(view);
    }


    @Override
    protected void convert(OrderItemVH helper, String item) {
        helper.tvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
