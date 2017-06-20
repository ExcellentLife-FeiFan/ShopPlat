package com.ytxd.spp.ui.adapter;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.ui.activity.order.AddCommentActivity;
import com.ytxd.spp.ui.activity.order.PayActivity;
import com.ytxd.spp.ui.adapter.viewholder.OrderItemVH;
import com.ytxd.spp.util.CommonUtils;

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

            }
        });
        helper.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddCommentActivity.class);
                mContext.startActivity(intent);
            }
        });
        helper.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PayActivity.class);
                intent.putExtra("data",item);
                mContext.startActivity(intent);
            }
        });
        CommonUtils.setText(helper.tvMerName,item.getName());
        CommonUtils.setText(helper.tvTime, item.getCreateTime().replace("T"," "));
        if(item.getGoods().size()==1){
            CommonUtils.setText(helper.tvGoodsDesr,item.getGoods().get(0).getGoodM().getGoodsTitle());
        }else{
            CommonUtils.setText(helper.tvGoodsDesr,item.getGoods().get(0).getGoodM().getGoodsTitle()+"等"+item.getGoods().size()+"种商品");
        }
        CommonUtils.setText(helper.tvTotalPrice,"￥"+item.getSJPrice());

        if(item.getOrderStateCode().equals(CommonUtils.UN_PAY)){
            CommonUtils.setText(helper.tvState,"等待支付");
            helper.tvPay.setVisibility(View.VISIBLE);
        }else if(item.getOrderStateCode().equals(CommonUtils.HAVE_PAY)){
            CommonUtils.setText(helper.tvState,"等待接单");
            helper.tvPay.setVisibility(View.GONE);
        }else {
            CommonUtils.setText(helper.tvState,"交易成功");
            helper.tvPay.setVisibility(View.GONE);
        }
//        ImageLoadUtil.setImageNP(item.getGoods().get(0).getGoodM().getLogoPaths(),helper.civ,mContext);
//        CommonUtils.setText(helper.tvMerName,item.getName());
//        CommonUtils.setText(helper.tvMerName,item.getName());
//

    }

}
