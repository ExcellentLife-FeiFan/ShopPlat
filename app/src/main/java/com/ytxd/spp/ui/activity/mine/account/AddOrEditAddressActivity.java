package com.ytxd.spp.ui.activity.mine.account;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
    AddressM addressM;

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
        CommonUtils.setEtClearListener(etAdD, ivEdAdD);
        CommonUtils.setEtClearListener(etName, ivEdName);
        CommonUtils.setEtClearListener(etPhone, ivEdPhone);
        addressM = (AddressM) getIntent().getSerializableExtra("data");
        if (null != addressM) {
            getBar().initActionBar("修改收货地址", R.drawable.ic_back_white, R.drawable.ic_garbage, this);
            CommonUtils.setText(etName, addressM.getContacts());
            CommonUtils.setText(etPhone, addressM.getPhone());
            if (addressM.getSex() == 1) {
                rbMan.setChecked(true);
            } else {
                rbWoman.setChecked(true);
            }
            CommonUtils.setText(tvAddress, addressM.getAddressTitle());
            CommonUtils.setText(etAdD, addressM.getAddressContent());
        } else {
            getBar().initActionBar("增加收货地址", R.drawable.ic_back_white, -1, this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_v_right:
                presenter.deleteAD(addressM.getSHAddressCode());
                break;
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.ll_address, R.id.btn_save, R.id.tv_contact})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
                startActivity(SelectADFromMapActivity.class);
                break;
            case R.id.btn_save:
                String name = etName.getText().toString();
                String phone = etPhone.getText().toString();
                String adDetail = etAdD.getText().toString();
                if (null != addressM) {
                    if (AbStrUtil.isEmpty(name)) {
                        showToast("请输入收货人姓名");
                    } else if (AbStrUtil.isEmpty(phone)) {
                        showToast("请输入收货人手机号");
                    } else if (!AbStrUtil.isMobileNo(phone)) {
                        showToast("请输入规范的手机号码");
                    } else if (AbStrUtil.isEmpty(adDetail)) {
                        showToast("请填写详细地址");
                    } else {
                        addressM.setContacts(name);
                        addressM.setPhone(phone);
                        addressM.setPhoneCheck(true);
                        if (null != poiItem) {
                            addressM.setAddressTitle(poiItem.getTitle());
                            addressM.setAddressDescribe(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
                            addressM.setLat(poiItem.getLatLonPoint().getLatitude() + "");
                            addressM.setLng(poiItem.getLatLonPoint().getLongitude() + "");
                        }
                        addressM.setAddressContent(adDetail);
                        if (rgSex.getCheckedRadioButtonId() == R.id.rb_man) {
                            addressM.setSex(1);
                        } else {
                            addressM.setSex(2);
                        }
                        presenter.editAD(addressM);

                    }


                } else {
                    if (AbStrUtil.isEmpty(name)) {
                        showToast("请输入收货人姓名");
                    } else if (AbStrUtil.isEmpty(phone)) {
                        showToast("请输入收货人手机号");
                    } else if (!AbStrUtil.isMobileNo(phone)) {
                        showToast("请输入规范的手机号码");
                    } else if (null == poiItem) {
                        showToast("请选择地区地址");
                    } else if (AbStrUtil.isEmpty(adDetail)) {
                        showToast("请填写详细地址");
                    } else {
                        AddressM addressM = new AddressM();
                        addressM.setContacts(name);
                        addressM.setPhone(phone);
                        addressM.setPhoneCheck(true);
                        addressM.setAddressTitle(poiItem.getTitle());
                        addressM.setAddressDescribe(poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
                        addressM.setAddressContent(adDetail);
                        addressM.setLat(poiItem.getLatLonPoint().getLatitude() + "");
                        addressM.setLng(poiItem.getLatLonPoint().getLongitude() + "");
                        if (rgSex.getCheckedRadioButtonId() == R.id.rb_man) {
                            addressM.setSex(1);
                        } else {
                            addressM.setSex(2);
                        }
                        presenter.addAD(addressM);
                    }
                }


                break;
            case R.id.tv_contact:
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI), 0);
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
        EventBus.getDefault().post(new RefreshADEvent());
        showToast("添加成功");
        AppManager.getInstance().killActivity(this);
    }

    @Override
    public void editSuccess() {
        EventBus.getDefault().post(new RefreshADEvent());
        showToast("保存成功");
        AppManager.getInstance().killActivity(this);
    }

    @Override
    public void deleteSuccess() {
        EventBus.getDefault().post(new RefreshADEvent());
        showToast("删除成功");
        AppManager.getInstance().killActivity(this);
    }


    public void onEvent(ADEditSelectEvent event) {
        poiItem = event.poiItem;
        CommonUtils.setText(tvAddress, poiItem.getTitle());
        CommonUtils.setText(etAdD, poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                // ContentProvider展示数据类似一个单个数据库表
                // ContentResolver实例带的方法可实现找到指定的ContentProvider并获取到ContentProvider的数据
                ContentResolver reContentResolverol = getContentResolver();
                // URI,每个ContentProvider定义一个唯一的公开的URI,用于指定到它的数据集
                Uri contactData = data.getData();
                // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
                Cursor cursor = managedQuery(contactData, null, null, null, null);
                cursor.moveToFirst();
                // 获得DATA表中的名字
                String username = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                // 条件为联系人ID
                String contactId = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.Contacts._ID));
                // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
                Cursor phone = reContentResolverol.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
                                + contactId, null, null);
                while (phone.moveToNext()) {
                    String usernumber = phone
                            .getString(phone
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    etPhone.setText(usernumber);
                    etName.setText(username);
                }

            }
        }

    }
}
