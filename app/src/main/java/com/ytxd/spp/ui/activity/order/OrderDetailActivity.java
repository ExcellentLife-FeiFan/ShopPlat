package com.ytxd.spp.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.ui.activity.main.MerchantDetailActivity;
import com.ytxd.spp.ui.adapter.OrderSubGoodsLV;
import com.ytxd.spp.ui.views.InListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.lv_sub_goods)
    InListView lvSubGoods;
    OrderSubGoodsLV mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        getBar().initActionBar("订单详情", this);
        mAdapter = new OrderSubGoodsLV(new ArrayList<ShoppingCartM.Goods>(), this);
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

    @OnClick({R.id.ll_shop, R.id.btn_one_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                startActivity(MerchantDetailActivity.class);
                break;
            case R.id.btn_one_more:
                break;
        }
    }
}
