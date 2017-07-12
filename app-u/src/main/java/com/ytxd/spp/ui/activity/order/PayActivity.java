package com.ytxd.spp.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.OrderChangevent;
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
    private String payType;
    OrderM orderM;
    int position=-1;

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
        position=getIntent().getIntExtra("position",-1);
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
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick(R.id.btn_pay)
    public void onViewClicked() {
        if (null != orderM) {
            presenter.payUpload(orderM.getOrderCode());
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void paySuccess() {
        showToast("支付成功");
//        EventBus.getDefault().post(new RefreshOrderListEvent());
        AppManager.getInstance().killActivity(this);
        AppManager.getInstance().killActivity(EnsureOrderActivity.class);
        if(position!=-1){
            EventBus.getDefault().post(new OrderChangevent(position,OrderM.HAVE_PAY_WATING_ACE));
        }
    }

    @Override
    public void pay() {

    }

    @Override
    public void showDialogs() {
        DialogUtils.showDialog(this,"正在支付....");
    }

    @Override
    public void dismissDialogs() {
        DialogUtils.dismissDialog();
    }
}
