package com.ytxd.spp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ytxd.spp.base.App;
import com.ytxd.spp.event.AMapLocationUpdateEvent;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2015/12/18.
 */
public class AMapLocationUtil implements AMapLocationListener {
    private static AMapLocationUtil instance;
    private AMapLocationClient mLocationClient;

    private AMapLocationUtil() {
        if (null == mLocationClient) {
            mLocationClient = new AMapLocationClient(App.context);
            AMapLocationClientOption option = new AMapLocationClientOption();
            option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            option.setOnceLocation(true);
            option.setNeedAddress(true);
            mLocationClient.setLocationOption(option);
            mLocationClient.setLocationListener(this);
        }
    }

    public static AMapLocationUtil getInstance() {
        if (instance == null) {
            instance = new AMapLocationUtil();
        }
        return instance;
    }

    /**
     * 更新现在的位置
     **/
    public void startLocation() {
        if (AbWifiUtil.isConnectivity(App.context)) {
            mLocationClient.startLocation();
            LogUtils.i("开始定位");
        } else {
            ToastUtil.showToastShort(App.context, "网络未连接！");
            EventBus.getDefault().post(new AMapLocationUpdateEvent(null));
        }

    }


    public void stopLocation() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
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


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null || aMapLocation.getErrorCode() != 0) {
            ToastUtil.showToastShort(App.context, "定位失败！");
            EventBus.getDefault().post(new AMapLocationUpdateEvent(null));
            return;
        }
        this.stopLocation();
        EventBus.getDefault().post(new AMapLocationUpdateEvent(aMapLocation));
    }


}
