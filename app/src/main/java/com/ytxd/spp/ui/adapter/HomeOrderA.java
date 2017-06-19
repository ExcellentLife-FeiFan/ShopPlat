package com.ytxd.spp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.ui.activity.order.AddCommentActivity;
import com.ytxd.spp.ui.adapter.viewholder.OrderItemVH;

import java.util.List;


public class HomeOrderA extends BaseQuickAdapter<OrderM, OrderItemVH> {
    public HomeOrderA(List<OrderM> data) {
        super(R.layout.item_order, data);
    }

    protected OrderItemVH createBaseViewHolder(View view) {
        return new OrderItemVH(view);
    }


    @Override
    protected void convert(OrderItemVH helper, OrderM item) {
        helper.tvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddCommentActivity.class);
                mContext.startActivity(intent);
            }
        });

    }

}
