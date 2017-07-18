package com.ytxd.sppm.ui.adapter.viewHolder;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.github.zagum.expandicon.ExpandIconView;
import com.ytxd.sppm.R;
import com.ytxd.sppm.ui.views.MyExpandableLayout;

/**
 * Created by XY on 2017/7/12.
 */

public class OrderItemVH extends BaseViewHolder {
    public View rootView;
    public TextView tvOrderCode;
    public TextView txtOrderCustomerName;
    public TextView txtOrderCustomerPhone;
    public TextView txtOrderCustomerAddress;
    public TextView txtSenderName;
    public TextView txtSenderPhone;
    public TextView txtOrderRemarks;
    public TextView txtOrderTime;
    public TextView tvPrice;
    public TextView tvState;
    public MyExpandableLayout expandActi;
    public ExpandIconView expandv;
    public RelativeLayout rlExpandv;
    public TextView tvReject;
    public TextView tvSelectStaff;
    public TextView tvAceOrder;
    public TextView tvSendNow;
    public TextView tvCancelReason;
    public LinearLayout llOrderBasic,llStaff,llCancel;
    public TextView tvEnsure;
    public TextView tvRefund;
    public TextView tvPrint;

    public OrderItemVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.tvOrderCode = (TextView) rootView.findViewById(R.id.tv_order_code);
        this.txtOrderCustomerName = (TextView) rootView.findViewById(R.id.txt_order_customer_name);
        this.txtOrderCustomerPhone = (TextView) rootView.findViewById(R.id.txt_order_customer_phone);
        this.txtOrderCustomerAddress = (TextView) rootView.findViewById(R.id.txt_order_customer_address);
        this.txtSenderName = (TextView) rootView.findViewById(R.id.txt_sender_name);
        this.txtSenderPhone = (TextView) rootView.findViewById(R.id.txt_sender_phone);
        this.txtOrderRemarks = (TextView) rootView.findViewById(R.id.txt_order_remarks);
        this.txtOrderTime = (TextView) rootView.findViewById(R.id.txt_order_time);
        this.tvPrice = (TextView) rootView.findViewById(R.id.tv_price);
        this.expandActi = (MyExpandableLayout) rootView.findViewById(R.id.expand_acti);
        this.expandv = (ExpandIconView) rootView.findViewById(R.id.expandv);
        this.rlExpandv = (RelativeLayout) rootView.findViewById(R.id.rl_expandv);
        this.tvReject = (TextView) rootView.findViewById(R.id.tv_reject);
        this.tvSelectStaff = (TextView) rootView.findViewById(R.id.tv_select_staff);
        this.tvAceOrder = (TextView) rootView.findViewById(R.id.tv_ace_order);
        this.llOrderBasic = (LinearLayout) rootView.findViewById(R.id.ll_order_basic);
        this.tvState= (TextView) rootView.findViewById(R.id.tv_state);
        this.tvSendNow= (TextView) rootView.findViewById(R.id.tv_send_now);
        this.llStaff = (LinearLayout) rootView.findViewById(R.id.ll_staff);
        this.tvEnsure= (TextView) rootView.findViewById(R.id.tv_ensure);
        this.tvRefund= (TextView) rootView.findViewById(R.id.tv_refund);
        this.tvCancelReason= (TextView) rootView.findViewById(R.id.txt_cancel_reason);
        this.llCancel= (LinearLayout) rootView.findViewById(R.id.ll_cancel);
        this.tvPrint= (TextView) rootView.findViewById(R.id.tv_print);
    }

}
