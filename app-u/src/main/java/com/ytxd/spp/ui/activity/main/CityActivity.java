package com.ytxd.spp.ui.activity.main;

import android.os.Bundle;

import com.ytxd.spp.event.AMapLocationUpdateEvent;
import com.ytxd.spp.util.AMapLocationUtil;
import com.ytxd.spp.util.HideUtil;
import com.zaaach.citypicker.CityPickerActivity;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

public class CityActivity extends CityPickerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideUtil.init(this);
        EventBus.getDefault().register(this);
        mCityAdapter.updateLocateState(LocateState.LOCATING, null);
        AMapLocationUtil.getInstance().startLocation();
        getCities();
    }

    private void getCities() {
        List<City> hots = new ArrayList<>();
        hots.add(new City("北京", "beijing"));
        hots.add(new City("上海", "shanghai"));
        hots.add(new City("广州", "guangzhou"));
        hots.add(new City("天津", "tianjin"));
        hots.add(new City("南京", "nanjing"));
        hots.add(new City("杭州", "hanzhou"));
        hots.add(new City("重庆", "chongqin"));
        hots.add(new City("成都", "chengdu"));
        setHotCities(hots);
    }

    public void onEvent(AMapLocationUpdateEvent event) {
        if (null != event.getaMapLocation()) {
            String city = event.getaMapLocation().getCity();
            String district = event.getaMapLocation().getDistrict();
            String location = StringUtils.extractLocation(city, district);
            mCityAdapter.updateLocateState(LocateState.SUCCESS, location);
        } else {
            //定位失败
            mCityAdapter.updateLocateState(LocateState.FAILED, null);
        }
    }

    @Override
    protected void lacationCityClick() {
        mCityAdapter.updateLocateState(LocateState.LOCATING, null);
        AMapLocationUtil.getInstance().startLocation();
    }
}
