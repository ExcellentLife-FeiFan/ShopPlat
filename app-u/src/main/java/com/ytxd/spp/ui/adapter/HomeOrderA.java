package com.ytxd.spp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.activity.order.AddCommentActivity;
import com.ytxd.spp.ui.activity.order.PayActivity;
import com.ytxd.spp.ui.adapter.viewholder.OrderItemVH;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;


public class HomeOrderA extends BaseQuickAdapter<OrderM, OrderItemVH> {
    public HomeOrderA(List<OrderM> data) {
        super(R.layout.item_order, data);
    }

    protected OrderItemVH createBaseViewHolder(View view) {
        return new OrderItemVH(view);
    }


    @Override
    protected void convert(OrderItemVH helper, final OrderM item) {
        helper.tvAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MerchantDetailActivity.class);
                intent.putExtra("data",item.getSuperMarketModel());
                intent.putExtra("orderCode",item.getOrderCode());
                mContext.startActivity(intent);
            }
        });
        helper.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddCommentActivity.class);
                intent.putExtra("data",item);
                intent.putExtra("position", getData().indexOf(item));
                mContext.startActivity(intent);
            }
        });
        helper.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayActivity.class);
                intent.putExtra("data", item);
                intent.putExtra("position", getData().indexOf(item));
                mContext.startActivity(intent);
            }
        });
        ImageLoadUtil.setImage(item.getSuperMarketModel().getLogoUrl(), helper.civ, mContext);
        CommonUtils.setText(helper.tvMerName, item.getSuperMarketModel().getName());
        CommonUtils.setText(helper.tvTime, item.getCreateTime().replace("T", " "));
        CommonUtils.setText(helper.tvTotalPrice, "￥" + item.getSJPrice());
        helper.tvPay.setVisibility(View.GONE);
        helper.tvAgain.setVisibility(View.GONE);
        helper.tvEvaluate.setVisibility(View.GONE);
        switch (item.getOrderStateCode()) {
            case OrderM.WATING_PAY:
                CommonUtils.setText(helper.tvState, "等待支付");
                helper.tvPay.setVisibility(View.VISIBLE);
                break;
            case OrderM.HAVE_PAY_WATING_ACE:
                CommonUtils.setText(helper.tvState, "等待接单");
                break;
            case OrderM.FASE_PAY_WATING_ACE:
                CommonUtils.setText(helper.tvState, "等待接单");
                break;
            case OrderM.HAVE_ACE_WATING_SEND:
                CommonUtils.setText(helper.tvState, "等待配送");
                break;
            case OrderM.SENDING:
                CommonUtils.setText(helper.tvState, "配送中");
                break;
            case OrderM.SUCCESS:
                CommonUtils.setText(helper.tvState, "交易成功");
                if(!CommonUtils.getBoolean(item.getIsEvaluate())){
                    helper.tvEvaluate.setVisibility(View.VISIBLE);
                }
                helper.tvAgain.setVisibility(View.VISIBLE);
                break;
            case OrderM.CANCEL:
                CommonUtils.setText(helper.tvState, "已取消");
                helper.tvAgain.setVisibility(View.VISIBLE);
                break;
        }

    }



}
