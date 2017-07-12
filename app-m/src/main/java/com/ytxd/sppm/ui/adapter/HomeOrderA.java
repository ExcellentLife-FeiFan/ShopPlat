package com.ytxd.sppm.ui.adapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.sppm.R;
import com.ytxd.sppm.model.OrderM;
import com.ytxd.sppm.presenter.OrderFMPresenter;
import com.ytxd.sppm.ui.adapter.viewHolder.OrderItemVH;
import com.ytxd.sppm.ui.dialog.SelectDeliveryStaffDialog;
import com.ytxd.sppm.ui.views.InListView;
import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.CommonUtils;
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

        if (AbStrUtil.isEmpty(item.getDeliveryStaffCode())) {
            helper.llStaff.setVisibility(View.GONE);
        } else {
            helper.llStaff.setVisibility(View.VISIBLE);
        }

        if (null != item.getDeliveryStaff()) {
            CommonUtils.setText(helper.txtSenderName, item.getDeliveryStaff().getDeliveryStaffName());
            CommonUtils.setText(helper.txtSenderPhone, item.getDeliveryStaff().getPhone());
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
                CommonUtils.setText(helper.tvState, "配送中");
                break;
            case OrderM.SUCCESS:
                CommonUtils.setText(helper.tvState, "交易成功");
                break;
            case OrderM.CANCEL:
                CommonUtils.setText(helper.tvState, "已取消");
                break;
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
        List<String> goods = CommonUtils.getSampleList(8);
        if (goods.size() > 2) {
            InListView listViewh = (InListView) helper.expandActi.getHeaderRelativeLayout().findViewById(R.id.lv);
            InListView listViewc = (InListView) helper.expandActi.getContentRelativeLayout().findViewById(R.id.lv);
            OrderGoodLV adapterH = new OrderGoodLV(goods.subList(0, 2), mContext);
            listViewh.setAdapter(adapterH);
            OrderGoodLV adapterC = new OrderGoodLV(goods.subList(2, goods.size()), mContext);
            listViewc.setAdapter(adapterC);
        } else {
            InListView listView = (InListView) helper.expandActi.getHeaderRelativeLayout().findViewById(R.id.lv);
            OrderGoodLV adapter = new OrderGoodLV(goods, mContext);
            listView.setAdapter(adapter);
        }
        CommonUtils.setText(helper.tvOrderCode, item.getOrderCode());
        CommonUtils.setText(helper.txtOrderCustomerName, item.getContacts());
        CommonUtils.setText(helper.txtOrderCustomerPhone, item.getPhone());
        CommonUtils.setText(helper.txtOrderCustomerAddress, item.getAddressDescribe());
        CommonUtils.setText(helper.txtOrderRemarks, item.getRemarks());
        CommonUtils.setText(helper.txtOrderTime, CommonUtils.getFormatTimeString(item.getCreateTime()));
        CommonUtils.setText(helper.tvPrice, "共计" + item.getSJPrice() + "元");
        helper.tvReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.tvAceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.aceOrder(item.getOrderCode(), getData().indexOf(item));
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
                if (null != item.getDeliveryStaff()) {
                    presenter.setSendding(item.getOrderCode(), item.getDeliveryStaff().getDeliveryStaffCode(), getData().indexOf(item));
                }else{
                    ToastUtil.showToastShort(mContext,"请选择配送员");
                }
            }
        });

    }


}
