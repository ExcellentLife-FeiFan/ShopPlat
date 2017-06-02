package com.ytxd.spp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ytxd.spp.base.App;
import com.ytxd.spp.event.LocationUpdateEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/12/18.
 */
public class BaiduLocationUtil {
    private static BaiduLocationUtil instance;
    private LocationClient mLocationClient;

    private BaiduLocationUtil() {
    }

    public static BaiduLocationUtil getInstance() {
        if (instance == null) {
            instance = new BaiduLocationUtil();
        }
        return instance;
    }

    /**
     * 更新现在的位置
     **/
    public void updataMyLocation() {
        if (AbWifiUtil.isConnectivity(App.context)) {
            mLocationClient = new LocationClient(App.context);
            mLocationClient.registerLocationListener(mLocationListener);
            LocationClientOption option = new LocationClientOption();
            option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式
            option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02
            option.setOpenGps(true);//打开gps
            option.setScanSpan(1000000);//设置发起定位请求的间隔时间为5000ms
            option.setIsNeedAddress(true);//返回的定位结果包含地址信息
            option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向
            mLocationClient.setLocOption(option);
            mLocationClient.start();
            LogUtils.i("开始定位");
        } else {
            ToastUtil.showToastShort(App.context, "网络未连接！");
        }

    }

    private BDLocationListener mLocationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                ToastUtil.showToastShort(App.context, "定位失败！");
                return;
            }
            EventBus.getDefault().post(new LocationUpdateEvent(bdLocation));
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }

    };

    public void stopLocation() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    public static void initGPS(final Activity activity) {
        LocationManager locationManager = (LocationManager) activity
                .getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(activity, "请打开GPS",
                    Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
            dialog.setMessage("请打开GPS");
            dialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            // 转到手机设置界面，用户设置GPS
                            Intent intent = new Intent(
                                    Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            activity.startActivityForResult(intent, 0); // 设置完成后返回到原来的界面

                        }
                    });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
    }


}
