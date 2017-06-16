package com.ytxd.spp.ui.activity.order;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.SelectAddressEvent;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.presenter.EnsureOrderPresenter;
import com.ytxd.spp.ui.activity.mine.AddressManaActivity;
import com.ytxd.spp.ui.adapter.OrderSubGoodsLV;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.view.IEnsureOrderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EnsureOrderActivity extends BaseActivity<EnsureOrderPresenter> implements View.OnClickListener,IEnsureOrderView {

    @BindView(R.id.lv_sub_goods)
    InListView lvSubGoods;
    OrderSubGoodsLV mAdapter;
    String merchantCode;
    ShoppingCartM shoppingCartM;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_phone)
    TextView tvAddressPhone;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.ll_no_address)
    RelativeLayout llNoAddress;
    @BindView(R.id.ll_distri_select)
    LinearLayout llDistriSelect;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.civ_merchant)
    CircleImageView civMerchant;
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.ll_shop)
    LinearLayout llShop;
    @BindView(R.id.tv_total_p)
    TextView tvTotalP;
    @BindView(R.id.tv_discount_p)
    TextView tvDiscountP;
    @BindView(R.id.tv_real_pay)
    TextView tvRealPay;
    @BindView(R.id.cb_p_online)
    CheckBox cbPOnline;
    @BindView(R.id.cb_p_onget)
    CheckBox cbPOnget;
    @BindView(R.id.tv_youhuiquan)
    TextView tvYouhuiquan;
    @BindView(R.id.ll_youhuiquan)
    LinearLayout llYouhuiquan;
    @BindView(R.id.tv_total_p_2)
    TextView tvTotalP2;
    @BindView(R.id.tv_distri_p)
    TextView tvDistriP;
    @BindView(R.id.tv_activity_discount)
    TextView tvActivityDiscount;
    @BindView(R.id.tv_youhuiquan_discount)
    TextView tvYouhuiquanDiscount;
    @BindView(R.id.tv_total_p_3)
    TextView tvTotalP3;
    @BindView(R.id.btn_pay)
    Button btnPay;
    AddressM addressM;
    MerchantM merchant;

    @Override
    protected void initPresenter() {
        presenter = new EnsureOrderPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensure_order);
        ButterKnife.bind(this);
        presenter.getADList();
        getBar().initActionBar("确认订单", this);
        merchantCode = getIntent().getStringExtra("merchantCode");
        mAdapter = new OrderSubGoodsLV(new ArrayList<ShoppingCartM.Goods>(), this);
        lvSubGoods.setAdapter(mAdapter);
        if (!AbStrUtil.isEmpty(merchantCode)) {
            QueryBuilder queryBuilder = new QueryBuilder(LocalShoppingCartM.class)
                    .whereEquals(LocalShoppingCartM.CARTCODE, merchantCode);
            List<LocalShoppingCartM> beans = App.liteOrm.query(queryBuilder);
            if (beans.size() > 0) {
                shoppingCartM = beans.get(0).getShoppingCartM();
                mAdapter.addItems(shoppingCartM.getGoods(), true);
                merchant=shoppingCartM.getMerchantM();
                if(null!=merchant){
                    CommonUtils.setText(tvMerchantName,merchant.getName());
                    ImageLoadUtil.setImageNP(merchant.getLogoUrl(),civMerchant,this);
                    tvTotalP.setText("总计 ¥"+shoppingCartM.getPirceTotal());
                    tvRealPay.setText("¥"+shoppingCartM.getPirceTotal());
                    tvTotalP2.setText("¥"+shoppingCartM.getPirceTotal());
                    tvTotalP3.setText("¥"+shoppingCartM.getPirceTotal());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }



    @OnClick({R.id.ll_address, R.id.ll_no_address, R.id.ll_remark, R.id.ll_shop, R.id.ll_youhuiquan, R.id.btn_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
            case R.id.ll_no_address:
                startActivity(AddressManaActivity.class,"selectAddress","true");
                break;
            case R.id.ll_remark:
                startActivity(OrderRemarkEditActivity.class);
                break;
            case R.id.ll_shop:
//                startActivity(MerchantDetailActivity.class);
                break;
            case R.id.ll_youhuiquan:
                startActivity(DiscountCouponActivity.class);
                break;
            case R.id.btn_pay:
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeAddress(AddressM address) {
         setAddressData(address);
    }
    private void setAddressData(AddressM address){
        if(null==address){
            llAddress.setVisibility(View.GONE);
            llNoAddress.setVisibility(View.VISIBLE);
        }else{
            addressM = address;
            llAddress.setVisibility(View.VISIBLE);
            llNoAddress.setVisibility(View.GONE);
            CommonUtils.setText(tvAddress,address.getAddressDescribe());
            CommonUtils.setText(tvAddressName,address.getContacts());
            CommonUtils.setText(tvAddressPhone,address.getPhone());

        }
    }
    public void onEvent(SelectAddressEvent event) {
        setAddressData(event.addressM);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        if(null!=presenter){
//            presenter.getADList();
//        }
    }
}
