package com.ytxd.spp.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.flyco.systembar.SystemBarHelper;
import com.ytxd.spp.R;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.adapter.AddressSearchLV;
import com.ytxd.spp.ui.adapter.AddressSearchLV2;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.LogUtils;
import com.zaaach.citypicker.CityPickerActivity;
import com.zaaach.citypicker.model.City;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SelectADFromMapActivity extends BaseActivity implements LocationSource, AMapLocationListener, PoiSearch.OnPoiSearchListener, GeocodeSearch.OnGeocodeSearchListener {


    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_title_left)
    TextView tvCity;
    @BindView(R.id.lv_nearly)
    ListView lv;
    @BindView(R.id.lv)
    ListView lvSearch;
    @BindView(R.id.ll_map)
    LinearLayout llMap;
    @BindView(R.id.rl_search)
    RelativeLayout rlSearch;
    @BindView(R.id.rl_progress)
    RelativeLayout rl_progress;


    AddressSearchLV mSearchAddAdapter;
    AddressSearchLV2 mNearAddAdapter;
    private String cityCurrent = "北京";
    AMap aMap1;
    PoiSearch poiSearch;
    private PoiSearch.Query query;// Poi查询条件类
    boolean isFirst = true;


    private OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private GeocodeSearch geocoderSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_select_address);
        HideUtil.init(this);
        SystemBarHelper.tintStatusBar(this, getResources().getColor(R.color.colorPrimary));
        ButterKnife.bind(this);
        map.onCreate(savedInstanceState);
        aMap1 = map.getMap();
        initMap();
        mNearAddAdapter = new AddressSearchLV2(new ArrayList<PoiItem>(), this);
        lv.setAdapter(mNearAddAdapter);
        mSearchAddAdapter = new AddressSearchLV(new ArrayList<PoiItem>(), this);
        lvSearch.setAdapter(mSearchAddAdapter);

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
                    llMap.setVisibility(View.VISIBLE);
                    rlSearch.setVisibility(View.GONE);
                    return;
                }
                llMap.setVisibility(View.GONE);
                rlSearch.setVisibility(View.VISIBLE);
                searchCity(cs.toString());
            }
        });


    }

    private void initMap() {
        aMap1.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                setUp(aMap1);
            }
        });

    }

    private void setUp(AMap amap) {
        UiSettings uiSettings = amap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleControlsEnabled(false);
        aMap1.setLocationSource(this);// 设置定位监听
        aMap1.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap1.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (!isFirst) {
                    searchN(new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude));
                    return;
                }
                isFirst = false;
            }
        });

    }


    @OnClick({R.id.rl_back, R.id.tv_title_left})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                this.onBackPressed();
                break;
            case R.id.tv_title_left:
                startActivityForResult(new Intent(this, CityActivity.class), REQUEST_CODE_PICK_CITY);
                break;
        }
    }


    @Override
    protected void onPause() {
        map.onPause();
        deactivate();
        super.onPause();
    }

    @Override
    protected void onResume() {
        map.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        map.onDestroy();
        if (null != mlocationClient) {
            mlocationClient.onDestroy();
        }
        super.onDestroy();
    }

   /* public void searchNearby(LatLonPoint point) {
        query = new PoiSearch.Query("", "", cityCurrent);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(point, 5000, true));//
        poiSearch.searchPOIAsyn();// 异步搜索
    }*/

    public void searchN(LatLonPoint point) {
        if (null == geocoderSearch) {
            geocoderSearch = new GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(this);
        }
        RegeocodeQuery query = new RegeocodeQuery(point, 200, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);
    }

    public void searchCity(String keyword) {
        if (AbStrUtil.isEmpty(cityCurrent)) {
            return;
        }
        rl_progress.setVisibility(View.VISIBLE);
//        animation_view.playAnimation();
        query = new PoiSearch.Query(keyword, "", cityCurrent);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();// 异步搜索
    }

    public void searchDetail(String keyword) {
        if (null == geocoderSearch) {
            geocoderSearch = new GeocodeSearch(this);
            geocoderSearch.setOnGeocodeSearchListener(this);
        }
        GeocodeQuery query = new GeocodeQuery(keyword, keyword);
        geocoderSearch.getFromLocationNameAsyn(query);
    }

    private static final int REQUEST_CODE_PICK_CITY = 233;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);   //this
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                City city = (City) data.getSerializableExtra(CityPickerActivity.KEY_PICKED_CITY);
                tvCity.setText(city.getAreaName());
                cityCurrent = city.getAreaName();
                searchDetail(cityCurrent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (rlSearch.getVisibility() == View.VISIBLE) {
            rl_progress.setVisibility(View.GONE);
//            animation_view.pauseAnimation();
            rlSearch.setVisibility(View.GONE);
            llMap.setVisibility(View.VISIBLE);
            etSearch.setText("");
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mlocationClient.stopLocation();
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                aMap1.moveCamera(CameraUpdateFactory.zoomTo(18));
                searchN(new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude()));
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
            }
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogUtils.e(i + "");
        rl_progress.setVisibility(View.GONE);
//        animation_view.pauseAnimation();
        mSearchAddAdapter.addItems(poiResult.getPois(), true);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        LogUtils.e(i + "");
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        List<PoiItem> items = regeocodeResult.getRegeocodeAddress().getPois();
        if (items.size() > 0) {
            items.get(0).setCityName(regeocodeResult.getRegeocodeAddress().getCity());
            items.get(0).setAdName(regeocodeResult.getRegeocodeAddress().getDistrict());
        }
        mNearAddAdapter.addItems(items, true);
        LogUtils.e(i + "");
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        LogUtils.e(i + "");
        if (geocodeResult.getGeocodeAddressList().size() > 0) {
            aMap1.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLatitude(), geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint().getLongitude()), 18));
        }

    }
}
