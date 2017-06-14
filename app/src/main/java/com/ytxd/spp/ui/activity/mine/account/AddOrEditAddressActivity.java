package com.ytxd.spp.ui.activity.mine.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.ADEditSelectEvent;
import com.ytxd.spp.event.RefreshADEvent;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.presenter.ADEditOrAddPresenter;
import com.ytxd.spp.ui.activity.main.SelectADFromMapActivity;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.view.IADEditOrAddView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class AddOrEditAddressActivity extends BaseActivity<ADEditOrAddPresenter> implements View.OnClickListener, IADEditOrAddView {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.iv_ed_name)
    ImageView ivEdName;
    @BindView(R.id.rb_man)
    RadioButton rbMan;
    @BindView(R.id.rb_woman)
    RadioButton rbWoman;
    @BindView(R.id.rg_sex)
    RadioGroup rgSex;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_ed_phone)
    ImageView ivEdPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_ad_d)
    EditText etAdD;
    @BindView(R.id.iv_ed_ad_d)
    ImageView ivEdAdD;
    PoiItem poiItem;

    @Override
    protected void initPresenter() {
        presenter = new ADEditOrAddPresenter(this, this);
        presenter.init();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_address);
        ButterKnife.bind(this);
        getBar().initActionBar("增加收货地址", R.drawable.ic_back_white, R.drawable.ic_garbage, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_right:

                break;
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.ll_address, R.id.btn_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                startActivity(SelectADFromMapActivity.class);
                break;
            case R.id.btn_save:
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String adDetail = etAdD.getText().toString();
                if (AbStrUtil.isEmpty(name)) {
                    showToast("请输入收货人姓名");
                } else if (AbStrUtil.isEmpty(phone)) {
                    showToast("请输入收货人手机号");
                } else if (!AbStrUtil.isMobileNo(phone)) {
                    showToast("请输入规范的手机号码");
                }else if(null==poiItem){
                    showToast("请选择地区地址");
                }
                else if(AbStrUtil.isEmpty(adDetail)){
                    showToast("请填写详细地址");
                }else{
                    AddressM addressM = new AddressM();
                    addressM.setContacts(name);
                    addressM.setPhone(phone);
                    addressM.setPhoneCheck(true);
                    addressM.setAddressTitle(poiItem.getTitle());
                    addressM.setAddressDescribe(poiItem.getCityName()+poiItem.getAdName()+poiItem.getSnippet());
                    addressM.setAddressContent(adDetail);
                    addressM.setLat(poiItem.getLatLonPoint().getLatitude()+"");
                    addressM.setLng(poiItem.getLatLonPoint().getLongitude()+"");
                    if (rgSex.getCheckedRadioButtonId()== R.id.rb_man) {
                        addressM.setSex(1);
                    }else{
                        addressM.setSex(2);
                    }
                    presenter.addAD(addressM);
                }
                break;
        }
    }

    @Override
    public void init() {

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
    public void addSuccess() {
        showToast("添加成功");
        AppManager.getInstance().killActivity(this);
        EventBus.getDefault().post(new RefreshADEvent());
    }

    @Override
    public void editSuccess() {

    }


    public void onEvent(ADEditSelectEvent event) {
        poiItem = event.poiItem;
        CommonUtils.setText(tvAddress, poiItem.getTitle());
        CommonUtils.setText(etAdD, poiItem.getCityName()+poiItem.getAdName()+poiItem.getSnippet());
    }
}
