package com.ytxd.sppm.ui.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.sppm.R;
import com.ytxd.sppm.model.OrderGoodM;
import com.ytxd.sppm.model.OrderM;
import com.ytxd.sppm.presenter.OrderFMPresenter;
import com.ytxd.sppm.ui.adapter.viewHolder.OrderItemVH;
import com.ytxd.sppm.ui.dialog.SelectDeliveryStaffDialog;
import com.ytxd.sppm.ui.views.InListView;
import com.ytxd.sppm.util.AbDateUtil;
import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.DialogUtils;
import com.ytxd.sppm.util.ToastUtil;

import java.util.List;


public class HomeOrderA extends BaseQuickAdapter<OrderM, OrderItemVH> {
    OrderFMPresenter presenter;

    public HomeOrderA(List<OrderM> data, OrderFMPresenter presenter) {
        super(R.layout.item_order_base, data);
        this.presenter = presenter;
    }

    protected OrderItemVH createBaseViewHolder(View view) {
        return new OrderItemVH(view);
    }


    @Override
    protected void convert(final OrderItemVH helper, final OrderM item) {

        helper.tvReject.setVisibility(View.GONE);
        helper.tvAceOrder.setVisibility(View.GONE);
        helper.tvSelectStaff.setVisibility(View.GONE);
        helper.tvSendNow.setVisibility(View.GONE);
        helper.tvEnsure.setVisibility(View.GONE);
        helper.tvRefund.setVisibility(View.GONE);
        helper.tvPrint.setVisibility(View.GONE);
        helper.llCancel.setVisibility(View.GONE);

        if (AbStrUtil.isEmpty(item.getDeliveryStaffCode())) {
            helper.llStaff.setVisibility(View.GONE);
        } else {
            helper.llStaff.setVisibility(View.VISIBLE);
        }
        CommonUtils.setText(helper.tvArriveTime, AbDateUtil.getStringByFormat(CommonUtils.getFormatTimeString(item.getSDTime()),AbDateUtil.dateFormatHM));

        if (null != item.getDeliveryStaffModel()) {
            CommonUtils.setText(helper.txtSenderName, item.getDeliveryStaffModel().getDeliveryStaffName());
            CommonUtils.setText(helper.txtSenderPhone, item.getDeliveryStaffModel().getPhone());
            if (!AbStrUtil.isEmpty(item.getDeliveryStaffModel().getDeliveryStaffName())) {
                helper.llStaff.setVisibility(View.VISIBLE);
            }
        }

        switch (item.getOrderStateCode()) {
            case OrderM.HAVE_PAY_WATING_ACE:
                helper.tvAceOrder.setVisibility(View.VISIBLE);
                helper.tvReject.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "等待接单");
                break;
            case OrderM.FASE_PAY_WATING_ACE:
                helper.tvAceOrder.setVisibility(View.VISIBLE);
                helper.tvReject.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "等待接单");
                break;
            case OrderM.HAVE_ACE_WATING_SEND:
                if (AbStrUtil.isEmpty(item.getDeliveryStaffCode())) {
                    helper.tvSelectStaff.setVisibility(View.VISIBLE);
                }
                helper.tvSendNow.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "等待配送");
                break;
            case OrderM.SENDING:
                helper.tvEnsure.setVisibility(View.VISIBLE);
                helper.tvPrint.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "配送中");
                break;
            case OrderM.SUCCESS:
                CommonUtils.setText(helper.tvState, "交易成功");
                break;
            case OrderM.CANCEL_M:
                helper.llCancel.setVisibility(View.VISIBLE);
                helper.tvRefund.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "已取消");
                break;
            case OrderM.CANCEL_U:
                helper.llCancel.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "已取消");
                break;
            case OrderM.HAVE_REFUND:
                helper.llCancel.setVisibility(View.VISIBLE);
                CommonUtils.setText(helper.tvState, "已退款");
                break;
        }

        if (item.getOrderStateCode().equals(OrderM.HAVE_REFUND) || item.getOrderStateCode().equals(OrderM.CANCEL_M)||item.getOrderStateCode().equals(OrderM.CANCEL_U)) {
            CommonUtils.setText(helper.tvCancelReason, item.getCancelInfo());
        }

        helper.rlExpandv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.expandv.switchState(true);
                if (helper.expandActi.isOpened()) {
                    helper.expandActi.hide();
                } else {
                    helper.expandActi.show();
                }
            }
        });
        List<OrderGoodM> goods = item.getChildrenGoods();
        if (goods.size() > 2) {
            InListView listViewh = (InListView) helper.expandActi.getHeaderRelativeLayout().findViewById(R.id.lv);
            InListView listViewc = (InListView) helper.expandActi.getContentRelativeLayout().findViewById(R.id.lv);
            OrderGoodLV adapterH = new OrderGoodLV(goods.subList(0, 2), mContext);
            listViewh.setAdapter(adapterH);
            OrderGoodLV adapterC = new OrderGoodLV(goods.subList(2, goods.size()), mContext);
            listViewc.setAdapter(adapterC);
            helper.rlExpandv.setVisibility(View.VISIBLE);
        } else {
            InListView listView = (InListView) helper.expandActi.getHeaderRelativeLayout().findViewById(R.id.lv);
            OrderGoodLV adapter = new OrderGoodLV(goods, mContext);
            listView.setAdapter(adapter);
            helper.rlExpandv.setVisibility(View.GONE);
        }
        String todayNo="n";
        if(item.getTodayNo()!=0){
            todayNo=item.getTodayNo()+"";
        }
        CommonUtils.setText(helper.tvOrderCode, AbDateUtil.getStringByFormat(CommonUtils.getFormatTimeString(item.getCreateTime()), AbDateUtil.dateFormatYMD) + " " + "第" + todayNo + "单");
        CommonUtils.setText(helper.txtOrderCustomerName, item.getContacts());
        CommonUtils.setText(helper.txtOrderCustomerPhone, item.getPhone());
        CommonUtils.setText(helper.txtOrderCustomerAddress, item.getAddressTitle() + "-" + item.getAddressContent());
        CommonUtils.setText(helper.txtOrderRemarks, item.getRemarks());
        CommonUtils.setText(helper.txtOrderTime, CommonUtils.getFormatTimeString(item.getCreateTime()));
        CommonUtils.setText(helper.tvPrice, "共计" + item.getSJPrice() + "元");
        helper.tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.showInputDialog(mContext, "提示", "你确定取消订单吗，并输入理由。", InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE, R.string.hint_cancel_order_reason, 1, 100, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        if (AbStrUtil.isEmpty(input.toString())) {
                            dialog.setContent("理由不能为空");
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                            presenter.cancel(item.getOrderCode(), item.getUserCouponCode(), input.toString(), getData().indexOf(item));
                        }
                    }

                });

            }
        });
        helper.tvAceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.aceOrder(item, getData().indexOf(item));
            }
        });
        helper.tvSelectStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDeliveryStaffDialog deliveryStaffDialog = new SelectDeliveryStaffDialog();
                Bundle data = new Bundle();
                data.putInt("position", getData().indexOf(item));
                deliveryStaffDialog.setArguments(data);
                deliveryStaffDialog.show(((AppCompatActivity) mContext).getFragmentManager(), "SelectDeliveryStaffDialog");
            }
        });
        helper.tvSendNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != item.getDeliveryStaffModel()) {
                    if (!AbStrUtil.isEmpty(item.getDeliveryStaffModel().getDeliveryStaffName())) {
                        presenter.setSendding(item, item.getDeliveryStaffModel().getDeliveryStaffCode(), getData().indexOf(item));
                        return;
                    }
                }
                ToastUtil.showToastShort(mContext, "请选择配送员");
            }
        });
        helper.tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.refundOrder(item, getData().indexOf(item));
            }
        });
        helper.tvEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.ensureOrder(item, getData().indexOf(item));
            }
        });

        helper.tvPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToastShort(mContext, "打印成功");
            }
        });
    }


}
