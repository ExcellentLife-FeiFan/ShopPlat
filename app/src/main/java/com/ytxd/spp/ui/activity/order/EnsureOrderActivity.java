package com.ytxd.spp.ui.activity.order;

import android.os.Bundle;
import android.view.View;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.activity.mine.AddressManaActivity;
import com.ytxd.spp.ui.adapter.OrderSubGoodsLV;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EnsureOrderActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.lv_sub_goods)
    InListView lvSubGoods;
    OrderSubGoodsLV mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensure_order);
        ButterKnife.bind(this);
        getBar().initActionBar("确认订单", this);
        mAdapter = new OrderSubGoodsLV(CommonUtils.getSampleList(7), this);
        lvSubGoods.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.ll_address, R.id.ll_distri_select, R.id.ll_remark, R.id.ll_shop, R.id.ll_youhuiquan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                startActivity(AddressManaActivity.class);
                break;
            case R.id.ll_distri_select:
                break;
            case R.id.ll_remark:
                startActivity(OrderRemarkEditActivity.class);
                break;
            case R.id.ll_shop:
                startActivity(MerchantDetailActivity.class);
                break;
            case R.id.ll_youhuiquan:
                startActivity(DiscountCouponActivity.class);
                break;
        }
    }
}
