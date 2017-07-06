package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.OrderChangevent;
import com.ytxd.spp.model.OrderGoodM;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderDetailActivity extends BaseActivity<OrderDetailPresenter> implements View.OnClickListener, IOrderDetailView {

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
        orderM = (OrderM) getIntent().getSerializableExtra("data");
        position = getIntent().getIntExtra("position", -1);
        mAdapter = new OrderSubGoodsLV2(new ArrayList<OrderGoodM>(), this);
        lvSubGoods.setAdapter(mAdapter);
        CommonUtils.setEmptyViewForSLV(this, rlLv, lvSubGoods);
        if (null != orderM) {
            CommonUtils.setText(tvMerName, orderM.getSuperMarketModel().getName());
            ImageLoadUtil.setImageNP(orderM.getSuperMarketModel().getLogoUrl(), civ, this);
            tvTotalP.setText("总计 ¥" + orderM.getYPrice());
            tvRealPay.setText("¥" + orderM.getSJPrice());
            float dp = Float.valueOf(orderM.getYPrice()) - Float.valueOf(orderM.getSJPrice());
            if (dp > 0) {
                tvDiscountP.setText("优惠 ¥" + CommonUtils.getFloatString2(dp));
            }
            CommonUtils.setText(tvOrderCode, orderM.getOrderCode());
            CommonUtils.setText(tvOrderTime, orderM.getCreateTime().replace("T", " "));
            CommonUtils.setText(tvAddress, orderM.getAddressDescribe());
            if (orderM.getOrderStateCode().equals(OrderM.WATING_PAY)) {
                CommonUtils.setText(tvOrderState, "等待支付");
                llPay.setVisibility(View.VISIBLE);
            } else if (orderM.getOrderStateCode().equals(OrderM.HAVE_PAY_WATING_ACE)) {
                CommonUtils.setText(tvOrderState, "等待接单");
            } else if (orderM.getOrderStateCode().equals(OrderM.CANCEL)) {
                CommonUtils.setText(tvOrderState, "已取消");
                btnOneMore.setVisibility(View.VISIBLE);
            } else if (orderM.getOrderStateCode().equals(OrderM.SENDING)) {
                CommonUtils.setText(tvOrderState, "正在配送");
                btnEnsure.setVisibility(View.VISIBLE);
            } else {
                CommonUtils.setText(tvOrderState, "交易成功");
                btnOneMore.setVisibility(View.VISIBLE);
            }
        }
        presenter.getGoodsInfo(orderM.getOrderCode());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.btn_pay, R.id.btn_cancel, R.id.btn_one_more, R.id.ll_shop, R.id.btn_ensure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                startActivity(MerchantDetailActivity.class, "data", orderM.getSuperMarketModel());
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
                    DialogUtils.showDialog(activity, "提示", "确定取消该订单吗？", new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if (which.name().equals(DialogAction.POSITIVE.name())) {
                                presenter.cancel(orderM.getOrderCode(), AbStrUtil.isEmpty(orderM.getUserCouponCode()) ? "0" : orderM.getUserCouponCode());
                            }
                        }
                    });
                }
                break;
            case R.id.btn_one_more:
                Intent intent = new Intent(this, MerchantDetailActivity.class);
                intent.putExtra("data",orderM.getSuperMarketModel());
                intent.putExtra("orderCode",orderM.getOrderCode());
                startActivity(intent);
                break;
            case R.id.btn_ensure:
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeGoodsSuccess(List<OrderGoodM> items) {
        if (null != items) {
            mAdapter.addItems(items, true);
        }
        msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void cancelSuccess() {
        showToast("订单取消成功");
        tvOrderState.setText("已取消");
        orderM.setOrderCode(OrderM.CANCEL);
        llPay.setVisibility(View.GONE);
        btnOneMore.setVisibility(View.VISIBLE);
        EventBus.getDefault().post(new OrderChangevent(position, OrderM.CANCEL));
    }

    @Override
    public void ensureSuccess() {

    }
}
