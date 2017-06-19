package com.ytxd.spp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.AMapLocationUpdateEvent;
import com.ytxd.spp.event.HomeAddressChangeEvent;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.model.HomeAddressM;
import com.ytxd.spp.presenter.HomeSelectACEPresenter;
import com.ytxd.spp.ui.adapter.AddressSearchLV;
import com.ytxd.spp.ui.adapter.SelectACEAdLV;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.util.AMapLocationUtil;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.view.IHomeSelectACEView;
import com.zaaach.citypicker.CityPickerActivity;
import com.zaaach.citypicker.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class SelectACEAddressActivity extends BaseActivity<HomeSelectACEPresenter> implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener,IHomeSelectACEView {

    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_loc_ad)
    TextView tvLocAd;
    @BindView(R.id.lv_ad_data)
    InListView lvAdData;
    @BindView(R.id.lv_near_ad)
    InListView lvNearAd;
    SelectACEAdLV selectACEAdLV;
    String city;
    AMapLocation loc;
    AddressSearchLV mNearAddAdapter;

    AddressSearchLV mSearchAddAdapter;

    @BindView(R.id.ll_loc)
    LinearLayout llLoc;
    @BindView(R.id.lv)
    ListView lvSearch;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;

    @BindView(R.id.rl_lv_ad_data)
    RelativeLayout rl_lv_ad_data;
    @BindView(R.id.rl_lv_near_ad)
    RelativeLayout rl_lv_near_ad;
    private HomeAddressM addressM;

    @Override
    protected void initPresenter() {
        presenter = new HomeSelectACEPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_aceaddress);
        ButterKnife.bind(this);
        getBar().initActionBar("选择收货地址", "", "", R.drawable.ic_back_white, -1, this);
        initView();
        presenter.getADList();
    }

    private void initView() {
        CommonUtils.setEmptyViewForSLV(this, rl_lv_ad_data, lvAdData);
        CommonUtils.setEmptyViewForSLV(this, rl_lv_near_ad, lvNearAd);
        selectACEAdLV = new SelectACEAdLV(new ArrayList<AddressM>(), this);
        lvAdData.setAdapter(selectACEAdLV);
        mNearAddAdapter = new AddressSearchLV(new ArrayList<PoiItem>(), this);
        lvNearAd.setAdapter(mNearAddAdapter);
        mSearchAddAdapter = new AddressSearchLV(new ArrayList<PoiItem>(), this);
        lvSearch.setAdapter(mSearchAddAdapter);
        lvNearAd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new HomeAddressChangeEvent(new HomeAddressM(mNearAddAdapter.getItem(position).getCityName(),mNearAddAdapter.getItem(position).getTitle()
                        , mNearAddAdapter.getItem(position).toString()
                        , mNearAddAdapter.getItem(position).getLatLonPoint())));
                AppManager.getInstance().killActivity(activity);
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new HomeAddressChangeEvent(new HomeAddressM(mNearAddAdapter.getItem(position).getCityName(),mSearchAddAdapter.getItem(position).getTitle()
                        , mSearchAddAdapter.getItem(position).toString()
                        , mSearchAddAdapter.getItem(position).getLatLonPoint())));
                AppManager.getInstance().killActivity(activity);
            }
        });


        AMapLocationUtil.getInstance().startLocation();
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                mSearchAddAdapter.clearItems();
                if (cs.length() <= 0) {
                    llLoc.setVisibility(View.VISIBLE);
                    rlSearch.setVisibility(View.GONE);
                    return;
                }
                llLoc.setVisibility(View.GONE);
                rlSearch.setVisibility(View.VISIBLE);
                searchCity(cs.toString());
            }
        });
    }

    public void searchN(LatLonPoint point) {
        GeocodeSearch geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    public void searchCity(String keyword) {
        if (AbStrUtil.isEmpty(city)) {
            showToast("城市数据错误");
            return;
        }
        rl_progress.setVisibility(View.VISIBLE);
//        animation_view.playAnimation();
        PoiSearch.Query query = new PoiSearch.Query(keyword, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.tv_city, R.id.tv_re_loc, R.id.tv_loc_ad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city:
                startActivityForResult(new Intent(this, CityActivity.class), REQUEST_CODE_PICK_CITY);
                break;
            case R.id.tv_re_loc:
                tvLocAd.setText(getString(R.string.loc_doing));
                AMapLocationUtil.getInstance().startLocation();
                break;
            case R.id.tv_loc_ad:
                String ad = tvLocAd.getText().toString();
                if (ad.equals("") || ad.equals(getString(R.string.loc_doing)) || ad.equals(getString(R.string.loc_fail)) || null == addressM) {
                    return;
                }
                EventBus.getDefault().post(new HomeAddressChangeEvent(addressM));
                AppManager.getInstance().killActivity(activity);
                break;
        }
    }

    public void onEvent(AMapLocationUpdateEvent event) {
        if (event.getaMapLocation() != null) {

            HomeAddressM address = new HomeAddressM();
            address.setTitle(event.getaMapLocation().getPoiName());
            address.setAddress(event.getaMapLocation().getAddress());
            address.setCity(event.getaMapLocation().getCity());
            address.setLatLng(new LatLonPoint(event.getaMapLocation().getLatitude(), event.getaMapLocation().getLongitude()));
            addressM = address;

            tvLocAd.setText(event.getaMapLocation().getAddress());
            loc = event.getaMapLocation();
            city = event.getaMapLocation().getCity();
            tvCity.setText(event.getaMapLocation().getCity());
            searchN(new LatLonPoint(event.getaMapLocation().getLatitude(), event.getaMapLocation().getLongitude()));
        } else {
            tvLocAd.setText(getString(R.string.loc_fail));
            tvCity.setText(getString(R.string.loc_fail));
        }

    }

    private static final int REQUEST_CODE_PICK_CITY = 233;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);   //this
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                City city = (City) data.getSerializableExtra(CityPickerActivity.KEY_PICKED_CITY);
                tvCity.setText(city.getAreaName());
                this.city = city.getAreaName();
            }
        }
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        List<PoiItem> items = regeocodeResult.getRegeocodeAddress().getPois();
        if (items.size() > 0) {
            items.get(0).setCityName(regeocodeResult.getRegeocodeAddress().getCity());
            items.get(0).setAdName(regeocodeResult.getRegeocodeAddress().getDistrict());
        }
        mNearAddAdapter.addItems(items, true);
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        rl_progress.setVisibility(View.GONE);
        mSearchAddAdapter.addItems(poiResult.getPois(), true);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onBackPressed() {
        if (rlSearch.getVisibility() == View.VISIBLE) {
            rl_progress.setVisibility(View.GONE);
            rlSearch.setVisibility(View.GONE);
            llLoc.setVisibility(View.VISIBLE);
            etSearch.setText("");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeSuccess(List<AddressM> items) {
        selectACEAdLV.addItems(items,true);
    }

    @Override
    public void lodeFailed() {

    }
}
