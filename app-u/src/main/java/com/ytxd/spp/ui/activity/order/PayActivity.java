package com.ytxd.spp.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.base.G;
import com.ytxd.spp.event.OrderChangevent;
import com.ytxd.spp.event.RefreshOrderListEvent;
import com.ytxd.spp.event.WeChatPaySuccessEvent;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.PayPresenter;
import com.ytxd.spp.ui.views.MutilRadioGroup;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.view.IPayView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class PayActivity extends BaseActivity<PayPresenter> implements View.OnClickListener, IPayView {

    @BindView(R.id.tv_mer_name)
    TextView tvMerName;
    @BindView(R.id.tv_real_pay)
    TextView tvRealPay;
    @BindView(R.id.mrg_pay)
    MutilRadioGroup mrgPay;
    @BindView(R.id.cb_p_wechat)
    RadioButton cbPWechat;
    @BindView(R.id.cb_p_alipay)
    RadioButton cbPAlipay;
    private String payType = "0002";
    OrderM orderM;
    int position = -1;
    boolean isFromEnsureOrderActivity = false;
    private IWXAPI api;

    @Override
    protected void initPresenter() {
        presenter = new PayPresenter(activity, this);
        presenter.init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        orderM = (OrderM) getIntent().getSerializableExtra("data");
        isFromEnsureOrderActivity = getIntent().getBooleanExtra("isFromEnsureOrderActivity", false);
        api = WXAPIFactory.createWXAPI(this, G.WeChatAppId);
        api.registerApp(G.WeChatAppId);
        position = getIntent().getIntExtra("position", -1);
        if (null != orderM) {
            CommonUtils.setText(tvMerName, orderM.getSuperMarketModel().getName());
            CommonUtils.setText(tvRealPay, "¥" + orderM.getSJPrice());
        }
        getBar().initActionBar("支付订单", this);
        mrgPay.setOnCheckedChangeListener(new MutilRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
                if (checkedId == R.id.cb_p_wechat) {
                    payType = "0002";
                } else if (checkedId == R.id.cb_p_alipay) {
                    payType = "0001";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                if (isFromEnsureOrderActivity) {
                    startActivity(OrderDetailActivity.class, "orderCode", orderM.getOrderCode());
                    AppManager.getInstance().killActivity(this);
                } else {
                    AppManager.getInstance().killActivity(this);
                }
                break;
        }

    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        presenter.pay(this, orderM, payType, api);
    }

    @Override
    public void init() {

    }

    @Override
    public void paySuccess() {
//        EventBus.getDefault().post(new RefreshOrderListEvent());
        AppManager.getInstance().killActivity(this);
        AppManager.getInstance().killActivity(EnsureOrderActivity.class);
        if (position != -1) {
            EventBus.getDefault().post(new OrderChangevent(position, OrderM.HAVE_PAY_WATING_ACE));
        }
        if (isFromEnsureOrderActivity) {
            EventBus.getDefault().post(new RefreshOrderListEvent());
            startActivity(MyOrderActivity.class);
        }
    }

    public void onEvent(WeChatPaySuccessEvent event) {
        if (event.isSuccess) {
            paySuccess();
        } else {
            showToast("微信支付失败或取消");
        }

    }

    @Override
    public void showDialogs() {
        DialogUtils.showDialog(this, "正在支付....");
    }

    @Override
    public void dismissDialogs() {
        DialogUtils.dismissDialog();
    }

    @Override
    public void showToast(String txt) {
        showToast(txt);
    }

    @Override
    public void payFailure() {

    }

    @Override
    public void onBackPressed() {
        if (isFromEnsureOrderActivity) {
            startActivity(OrderDetailActivity.class, "orderCode", orderM.getOrderCode());
            AppManager.getInstance().killActivity(this);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick({R.id.ll_wechat, R.id.ll_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_wechat:
                cbPWechat.setChecked(!cbPWechat.isChecked());
                break;
            case R.id.ll_alipay:
                cbPAlipay.setChecked(!cbPAlipay.isChecked());
                break;
        }
    }
}
