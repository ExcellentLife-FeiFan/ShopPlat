package com.ytxd.spp.ui.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.OrderPresenter;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.activity.order.AddCommentActivity;
import com.ytxd.spp.ui.activity.order.PayActivity;
import com.ytxd.spp.ui.adapter.viewholder.OrderItemVH;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;


public class HomeOrderA extends BaseQuickAdapter<OrderM, OrderItemVH> {
    OrderPresenter presenter;

    public HomeOrderA(List<OrderM> data, OrderPresenter presenter) {
        super(R.layout.item_order, data);
        this.presenter = presenter;
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
                intent.putExtra("merchantCode", item.getSuperMarketModel().getSupermarketCode());
                intent.putExtra("orderCode", item.getOrderCode());
                mContext.startActivity(intent);
            }
        });
        helper.tvEvaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddCommentActivity.class);
                intent.putExtra("data", item);
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
        helper.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getOrderStateCode().equals(OrderM.WATING_PAY)) {
                    DialogUtils.showInputDialog(mContext, "提示", "你确定取消订单吗，并输入理由。", InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE, R.string.hint_cancel_order_reason, 1, 100, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if (AbStrUtil.isEmpty(input.toString())) {
                                dialog.setContent("理由不能为空");
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                            } else {
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                presenter.cancel(getData().indexOf(item), item.getOrderCode(), item.getUserCouponCode(), input.toString());
                            }
                        }

                    });
                } else {
                    DialogUtils.showDialog(mContext, "提示", "此刻取消订单请联系商家\n" + item.getSuperMarketModel().getContacts() + "：" + item.getSuperMarketModel().getPhone(), new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which.name().equals(DialogAction.POSITIVE.name())) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri data = Uri.parse("tel:" + item.getSuperMarketModel().getPhone());
                                intent.setData(data);
                                mContext.startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
        helper.tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ensure(getData().indexOf(item), item.getOrderCode());
            }
        });
        helper.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showDialog(mContext, "提示", "确定删除该订单吗？", new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which.name().equals(DialogAction.POSITIVE.name())) {
                            presenter.delete(getData().indexOf(item), item.getOrderCode());
                        }
                    }
                });
            }
        });
        ImageLoadUtil.setImage(item.getSuperMarketModel().getLogoUrl(), helper.civ, mContext);
        CommonUtils.setText(helper.tvMerName, item.getSuperMarketModel().getName());
        CommonUtils.setText(helper.tvTime, item.getCreateTime().replace("T", " "));
        CommonUtils.setText(helper.tvTotalPrice, "￥" + item.getSJPrice());
        if (item.getChildrenGoods().size() > 0) {
            if (item.getChildrenGoods().size() > 1) {
                CommonUtils.setText(helper.tvGoodsDesr, item.getChildrenGoods().get(0).getGoodsTitle() + "等" + item.getChildrenGoods().size() + "件商品");
            } else {
                CommonUtils.setText(helper.tvGoodsDesr, item.getChildrenGoods().get(0).getGoodsTitle());
            }

        }
        helper.tvPay.setVisibility(View.GONE);
        helper.tvAgain.setVisibility(View.GONE);
        helper.tvEvaluate.setVisibility(View.GONE);
        helper.tvCancel.setVisibility(View.GONE);
        helper.tvEnsure.setVisibility(View.GONE);
        helper.tvDelete.setVisibility(View.GONE);
        switch (item.getOrderStateCode()) {
            case OrderM.WATING_PAY:
                CommonUtils.setText(helper.tvState, "等待支付");
                helper.tvPay.setVisibility(View.VISIBLE);
                helper.tvCancel.setVisibility(View.VISIBLE);
                break;
            case OrderM.HAVE_PAY_WATING_ACE:
                CommonUtils.setText(helper.tvState, "等待接单");
                helper.tvCancel.setVisibility(View.VISIBLE);
                break;
            case OrderM.FASE_PAY_WATING_ACE:
                CommonUtils.setText(helper.tvState, "等待接单");
                helper.tvCancel.setVisibility(View.VISIBLE);
                break;
            case OrderM.HAVE_ACE_WATING_SEND:
                CommonUtils.setText(helper.tvState, "等待配送");
                helper.tvCancel.setVisibility(View.VISIBLE);
                break;
            case OrderM.SENDING:
                CommonUtils.setText(helper.tvState, "配送中");
                helper.tvCancel.setVisibility(View.VISIBLE);
                helper.tvEnsure.setVisibility(View.VISIBLE);
                break;
            case OrderM.SUCCESS:
                CommonUtils.setText(helper.tvState, "交易成功");
                if (!CommonUtils.getBoolean(item.getIsEvaluate())) {
                    helper.tvEvaluate.setVisibility(View.VISIBLE);
                }
                helper.tvAgain.setVisibility(View.VISIBLE);
                helper.tvDelete.setVisibility(View.VISIBLE);
                break;
            case OrderM.CANCEL_U:
                CommonUtils.setText(helper.tvState, "已取消");
                helper.tvAgain.setVisibility(View.VISIBLE);
                helper.tvDelete.setVisibility(View.VISIBLE);
                break;
            case OrderM.CANCEL_M:
                CommonUtils.setText(helper.tvState, "已取消");
                helper.tvAgain.setVisibility(View.VISIBLE);
                break;
            case OrderM.HAVE_REFUND:
                CommonUtils.setText(helper.tvState, "已退款");
                helper.tvAgain.setVisibility(View.VISIBLE);
                helper.tvDelete.setVisibility(View.VISIBLE);
                break;
        }

    }


}
