package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.services.core.LatLonPoint;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.CartListClearRefreshEvent;
import com.ytxd.spp.event.RefreshOrderListEvent;
import com.ytxd.spp.event.SelectAddressEvent;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.model.LocalShoppingCartM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.presenter.EnsureOrderPresenter;
import com.ytxd.spp.ui.activity.mine.AddressManaActivity;
import com.ytxd.spp.ui.activity.mine.DiscountCouponActivity;
import com.ytxd.spp.ui.adapter.OrderSubGoodsLV;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.ui.views.MutilRadioGroup;
import com.ytxd.spp.util.AbDateUtil;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.ShoppingCartUtil;
import com.ytxd.spp.view.IEnsureOrderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;

public class EnsureOrderActivity extends BaseActivity<EnsureOrderPresenter> implements View.OnClickListener, IEnsureOrderView {

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
    @BindView(R.id.tv_youhuiquan)
    TextView tvYouhuiquan;
    @BindView(R.id.ll_youhuiquan)
    LinearLayout llYouhuiquan;
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
    @BindView(R.id.mrg_pay)
    MutilRadioGroup mrgPay;
    String payType = "0002";
    String remark = "无其他要求";
    public static final int REMARKS = 1001;
    String goodsInfo = "";
    float yp/*原价*/, sp/*实际支付*/, dp/*优惠价格*/;
    MerchantM.ManJianBean manJian;
    CouponM couponM;

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
        merchant = (MerchantM) getIntent().getSerializableExtra("merchant");
        merchantCode = merchant.getSupermarketCode();
        mAdapter = new OrderSubGoodsLV(new ArrayList<ShoppingCartM.Goods>(), this);
        lvSubGoods.setAdapter(mAdapter);
        if (!AbStrUtil.isEmpty(merchantCode)) {
            List<LocalShoppingCartM> beans = ShoppingCartUtil.getLocalShoppingCartMs(merchantCode);
            if (beans.size() > 0) {
                shoppingCartM = beans.get(0).getShoppingCartM();
//                merchant = shoppingCartM.getMerchantM();
                for (int i = 0; i < shoppingCartM.getGoods().size(); i++) {
                    if (i == shoppingCartM.getGoods().size() - 1) {
                        goodsInfo = goodsInfo + shoppingCartM.getGoods().get(i).getGoodM().getGoodsCode() + "[" + shoppingCartM.getGoods().get(i).getCount();
                    } else {
                        goodsInfo = goodsInfo + shoppingCartM.getGoods().get(i).getGoodM().getGoodsCode() + "[" + shoppingCartM.getGoods().get(i).getCount() + ",";
                    }
                }
                //计算满减优惠/////////////////////////////
                List<MerchantM.ManJianBean> manJianBeans = merchant.getManJian();
                for (int i = 0; i < manJianBeans.size() - 1; i++) {
                    for (int j = 0; j < manJianBeans.size() - 1 - i; j++) {
                        if (manJianBeans.get(j).getMMoney() > manJianBeans.get(j + 1).getMMoney()) {
                            MerchantM.ManJianBean temp = manJianBeans.get(j);
                            manJianBeans.set(j, manJianBeans.get(j + 1));
                            manJianBeans.set(j + 1, temp);
                        }
                    }
                }
                Collections.reverse(manJianBeans);
                manJian = null;
                yp = Float.valueOf(shoppingCartM.getPirceTotal());
                for (int i = 0; i < manJianBeans.size(); i++) {
                    float mm = manJianBeans.get(i).getMMoney();
                    if (yp >= mm) {
                        manJian = manJianBeans.get(i);
                        break;
                    }
                }
                sp = yp;
                if (null != manJian) {
                    sp = sp - manJian.getJMoney();
                    dp = manJian.getJMoney();
                    tvActivityDiscount.setText("¥" + manJian.getJMoney());
                }
                ///////////////////////////////////
                mAdapter.addItems(shoppingCartM.getGoods(), true);
                if (null != merchant) {
                    float ps = 0.0f;
                    try {
                        ps = Float.valueOf(merchant.getPSPrice());
                    } catch (Exception e) {
                        merchant.setPSPrice("0.0");
                        e.printStackTrace();
                    }
                    sp = sp + ps;
                    yp = yp + ps;
                    CommonUtils.setText(tvMerchantName, merchant.getName());
                    CommonUtils.setText(tvDistriP, "¥" + merchant.getPSPrice());
                    ImageLoadUtil.setImageNP(merchant.getLogoUrl(), civMerchant, this);
                    tvTotalP.setText("总计 ¥" + yp);
                    addCouponDiscount(null);
                }
            }
        }

    }

    private void addCouponDiscount(CouponM couponM) {
        if (null != this.couponM) {
            float cp = Float.valueOf(this.couponM.getCouponPrice());
            dp = dp - cp;
            sp = sp + cp;
        }
        if (null != couponM) {
            float cp2 = Float.valueOf(couponM.getCouponPrice());
            tvYouhuiquanDiscount.setText("¥" + cp2);
            dp = dp + cp2;
            sp = sp - cp2;
            this.couponM = couponM;
        }
        tvRealPay.setText("¥" + CommonUtils.getFloatString2(sp));
        tvTotalP3.setText("¥" + CommonUtils.getFloatString2(sp));
        tvDiscountP.setText("¥" + CommonUtils.getFloatString2(dp));
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
                startActivity(AddressManaActivity.class, "selectAddress", "true");
                break;
            case R.id.ll_remark:
                startActivityForResult(OrderRemarkEditActivity.class, REMARKS);
                break;
            case R.id.ll_shop:
//                startActivity(MerchantDetailActivity.class);
                break;
            case R.id.ll_youhuiquan:
                Intent intent = new Intent(activity, DiscountCouponActivity.class);
                intent.putExtra("isSelect", "true");
                intent.putExtra("price" +
                        "" +
                        "", shoppingCartM.getPirceTotal());
                startActivityForResult(intent, 1);
                break;
            case R.id.btn_pay:
                if (null == addressM) {
                    showToast("请选择收货地址");
                } else {
                    OrderM orderM = new OrderM();
                    orderM.setGoodsInfo(goodsInfo);
                    orderM.setPayType(payType);
                    orderM.setManJianCode(null == manJian ? "0" : manJian.getManJianCode());
                    orderM.setUserCouponCode(null == couponM ? "0" : couponM.getCouponCode());
                    orderM.setRemarks(remark);
                    orderM.setSHAddressCode(addressM.getSHAddressCode());
                    orderM.setSJPrice(CommonUtils.getFloatString2(sp) + "");
                    orderM.setYPrice(CommonUtils.getFloatString2(yp) + "");
                    orderM.setSupermarketCode(merchantCode);
                    orderM.setSuperMarketModel(merchant);
                    orderM.setSDTime(AbDateUtil.getStringByFormat(new Date(), AbDateUtil.dateFormatYMDHMS));
                    orderM.setPSPrice(merchant.getPSPrice());
                    presenter.ensureOrder(orderM);
                }


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

    @Override
    public void showDialogs() {
        showDialog();
    }

    @Override
    public void dismissDialogs() {
        dismissDialog();
    }

    @Override
    public void ensureOrderSuccess(OrderM orderM) {
        ShoppingCartUtil.deleteCart(this, merchantCode);
        showToast("订单提交成功");
        startActivity(PayActivity.class, "data", orderM);
        EventBus.getDefault().post(new CartListClearRefreshEvent());
        EventBus.getDefault().post(new RefreshOrderListEvent());
        AppManager.getInstance().killActivity(this);
    }

    private void setAddressData(AddressM address) {
        btnPay.setText("立即支付");
        if (null == address) {
            llAddress.setVisibility(View.GONE);
            llNoAddress.setVisibility(View.VISIBLE);
            btnPay.setEnabled(false);
        } else {
            addressM = address;
            llAddress.setVisibility(View.VISIBLE);
            llNoAddress.setVisibility(View.GONE);
            CommonUtils.setText(tvAddress, address.getAddressTitle()+address.getAddressContent());
            CommonUtils.setText(tvAddressName, address.getContacts());
            CommonUtils.setText(tvAddressPhone, address.getPhone());
            if (!CommonUtils.isInConfines(merchant,new LatLonPoint(Double.valueOf(addressM.getLat()), Double.valueOf(addressM.getLng())))) {
                btnPay.setEnabled(false);
                btnPay.setText("超配送范围");
            }else{
                btnPay.setEnabled(true);
            }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == REMARKS) {
            remark = data.getStringExtra("remark");
            tvRemark.setText(remark);
        }
        if (resultCode == 1) {
            CouponM couponM = (CouponM) data.getSerializableExtra("coupon");
            addCouponDiscount(couponM);
            tvYouhuiquan.setText(couponM.getCouponName());
            CommonUtils.setTextColor(tvYouhuiquan, R.color.colorPrimary);
        }
    }
}
