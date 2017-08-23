package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.OrderChangevent;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.OrderDetailPresenter;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.adapter.OrderSubGoodsLV2;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.view.IOrderDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements View.OnClickListener, IOrderDetailView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.lv_sub_goods)
    InListView lvSubGoods;
    @BindView(R.id.rl_lv)
    RelativeLayout rlLv;
    OrderSubGoodsLV2 mAdapter;
    OrderM orderM;
    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_mer_name)
    TextView tvMerName;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_total_p)
    TextView tvTotalP;
    @BindView(R.id.tv_discount_p)
    TextView tvDiscountP;
    @BindView(R.id.tv_real_pay)
    TextView tvRealPay;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_distri_type)
    TextView tvDistriType;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.btn_pay)
    Button btnPay;
    @BindView(R.id.btn_one_more)
    Button btnOneMore;
    @BindView(R.id.msv)
    MultiStateView msv;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    @BindView(R.id.btn_ensure)
    Button btnEnsure;
    int position = -1;
    @BindView(R.id.tv_distr_time)
    TextView tvDistrTime;
    @BindView(R.id.tv_distr_p)
    TextView tvDistrP;
    @BindView(R.id.btn_cancel_c)
    Button btnCancelC;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.srollview)
    ScrollView srollview;
    private String orderCode;


    @Override
    protected void initPresenter() {
        presenter = new OrderDetailPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        getBar().initActionBar("订单详情", this);
//        orderM = (OrderM) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("position", -1);
        srl.setOnRefreshListener(this);

        srollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (srl != null) {
                    srl.setEnabled(srollview.getScrollY() == 0);
                }
            }
        });

        orderCode = getIntent().getStringExtra("orderCode");
        CommonUtils.setEmptyViewForSLV(this, rlLv, lvSubGoods);
        presenter.getOrderInfo(orderCode);
      /*  if (null != orderM) {
            setData(orderM);
        } else {
            presenter.getOrderInfo(orderCode);
        }*/
    }

    private void setData(OrderM orderM) {
        this.orderM = orderM;
        mAdapter = new OrderSubGoodsLV2(orderM.getChildrenGoods(), this);
        lvSubGoods.setAdapter(mAdapter);
        CommonUtils.setText(tvMerName, orderM.getSuperMarketModel().getName());
        ImageLoadUtil.setImageNP(orderM.getSuperMarketModel().getLogoUrl(), civ, this);
        tvTotalP.setText("总计 ¥" + orderM.getYPrice());
        tvRealPay.setText("¥" + orderM.getSJPrice());
        float dp = Float.valueOf(orderM.getYPrice()) - Float.valueOf(orderM.getSJPrice());
        if (dp > 0) {
            tvDiscountP.setText("优惠 ¥" + CommonUtils.getFloatString2(dp));
        }
        CommonUtils.setText(tvDistrTime, CommonUtils.getSDTimeDesrcDay(orderM.getSDTime()));
        CommonUtils.setText(tvOrderCode, orderM.getOrderCode());
        CommonUtils.setText(tvOrderTime, orderM.getCreateTime().replace("T", " "));
        CommonUtils.setText(tvAddress, orderM.getAddressTitle() + orderM.getAddressContent());
        CommonUtils.setText(tvDistrP, "¥" + orderM.getPSPrice());

        if (orderM.getOrderStateCode().equals(OrderM.WATING_PAY)) {
            CommonUtils.setText(tvOrderState, "等待支付");
            llPay.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.HAVE_PAY_WATING_ACE)) {
            CommonUtils.setText(tvOrderState, "等待接单");
            btnCancelC.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.FASE_PAY_WATING_ACE)) {
            CommonUtils.setText(tvOrderState, "等待接单");
            btnCancelC.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.HAVE_ACE_WATING_SEND)) {
            CommonUtils.setText(tvOrderState, "等待配送");
            btnCancelC.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.SENDING)) {
            CommonUtils.setText(tvOrderState, "正在配送");
            btnEnsure.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.SUCCESS)) {
            CommonUtils.setText(tvOrderState, "交易成功");
            btnOneMore.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.CANCEL_U)) {
            CommonUtils.setText(tvOrderState, "已取消");
            btnOneMore.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.CANCEL_M)) {
            CommonUtils.setText(tvOrderState, "已取消");
            btnOneMore.setVisibility(View.VISIBLE);
        } else if (orderM.getOrderStateCode().equals(OrderM.HAVE_REFUND)) {
            CommonUtils.setText(tvOrderState, "已退款");
            btnOneMore.setVisibility(View.VISIBLE);
        }
        if (!AbStrUtil.isEmpty(orderM.getDeliveryStaffModel().getDeliveryStaffName())) {
            CommonUtils.setText(tvDistriType, "骑手" + "(" + orderM.getDeliveryStaffModel().getDeliveryStaffName() + "  " + orderM.getDeliveryStaffModel().getPhone() + ")");
        }
        msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.btn_pay, R.id.btn_cancel, R.id.btn_cancel_c, R.id.btn_one_more, R.id.ll_shop, R.id.btn_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                startActivity(MerchantDetailActivity.class, "merchantCode", orderM.getSuperMarketModel().getSupermarketCode());
                break;
            case R.id.btn_pay:
                if (null != orderM) {
                    Intent intent = new Intent(activity, PayActivity.class);
                    intent.putExtra("data", orderM);
                    intent.putExtra("position", position);
                    startActivity(intent);
                }
                break;
            case R.id.btn_cancel:
                if (null != orderM) {
                    DialogUtils.showInputDialog(this, "提示", "你确定取消订单吗，并输入理由。", InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE, R.string.hint_cancel_order_reason, 1, 100, new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                            if (AbStrUtil.isEmpty(input.toString())) {
                                dialog.setContent("理由不能为空");
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                            } else {
                                dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                                presenter.cancel(position, orderM.getOrderCode(), orderM.getUserCouponCode(), input.toString());
                            }
                        }

                    });

                 /*   DialogUtils.showDialog(activity, "提示", "确定取消该订单吗？", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which.name().equals(DialogAction.POSITIVE.name())) {
                                presenter.cancel(orderM.getOrderCode(), orderM.getUserCouponCode(),input.toString());
                            }
                        }
                    });*/
                }
                break;
            case R.id.btn_one_more:
                Intent intent = new Intent(this, MerchantDetailActivity.class);
                intent.putExtra("merchantCode", orderM.getSuperMarketModel().getSupermarketCode());
                intent.putExtra("orderCode", orderM.getOrderCode());
                startActivity(intent);
                break;
            case R.id.btn_ensure:
                presenter.ensure(position, orderM.getOrderCode());
                break;
            case R.id.btn_cancel_c:
                DialogUtils.showDialog(activity, "提示", "此刻取消订单请联系商家\n" + orderM.getSuperMarketModel().getContacts() + "：" + orderM.getSuperMarketModel().getPhone(), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        if (which.name().equals(DialogAction.POSITIVE.name())) {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + orderM.getSuperMarketModel().getPhone());
                            intent.setData(data);
                            startActivity(intent);
                        }
                    }
                });
                break;

        }
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeOrderSuccess(OrderM orderM) {
        if (null != orderM) {
            this.orderM = orderM;
            setData(orderM);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        srl.setRefreshing(false);

    }

    @Override
    public void cancelSuccess(int p) {
        showToast("订单取消成功");
        tvOrderState.setText("已取消");
        orderM.setOrderCode(OrderM.CANCEL_U);
        llPay.setVisibility(View.GONE);
        btnOneMore.setVisibility(View.VISIBLE);
        if (position != -1) {
            EventBus.getDefault().post(new OrderChangevent(position, OrderM.CANCEL_U));
        }
    }

    @Override
    public void ensureSuccess(int p) {
        if (position != -1) {
            EventBus.getDefault().post(new OrderChangevent(position, OrderM.SUCCESS));
        }
    }

    @Override
    public void deleteSuccess(int position) {

    }

    @Override
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dissmisDialogs() {
        dismissDialog();
    }

    @Override
    public void onRefresh() {
        presenter.getOrderInfo(getIntent().getStringExtra("orderCode"));
    }
}
