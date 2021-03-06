package com.ytxd.spp.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.G;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.ui.activity.login.LoginActivity;
import com.ytxd.spp.ui.views.loadview.CustomDialog;

import java.io.File;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import static com.ytxd.spp.base.App.context;
import static com.ytxd.spp.base.App.user;


/**
 * Created by XY on 2016/11/20.
 */

public class CommonUtils {

    public static final int REFRESH = 1;
    public static final int LODEMORE = 2;
    public static final String UN_PAY = "0001";
    public static final String HAVE_PAY = "0002";
    private static CustomDialog dialog;

/*    public static boolean isLogined(final Activity context) {
        if (null != null) {
            return true;
        } else {
//            context.startActivity(new Intent(context, LoginActivity.class));
     *//*       DialogUtils.showDialog(context, "您还没有登录哦！", "", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which.name().equals(DialogAction.POSITIVE.name())) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                        AppManager.getInstance().killActivity(context);
                    }

                }
            });*//*
            return false;
        }
    }*/

    public static boolean isLogined(Context context) {
        if (null != user) {
            return true;
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return false;
        }
    }

    public static boolean isLogined(Activity context, boolean isPayback) {
        if (null != user) {
            return true;
        } else {
            Intent intent = new Intent(context, LoginActivity.class);
            if (isPayback) {
                intent.putExtra("payback", "payback");
            }
            context.startActivityForResult(intent, 1001);
            return false;
        }
    }

    public static boolean isLogined2() {
        if (null != user) {
            return true;
        } else {
            return false;
        }
    }

    public static void setEmptyView(Activity context, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        context.addContentView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyView(Context context, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) (listView.getRootView())).addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForFragment(Context context, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.common_empty_view, null);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForSLV(Context context, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_lv_common_empty, null);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static void setEmptyViewForSLV(View empty, AbsListView listView) {
        empty.setVisibility(View.GONE);
        listView.setEmptyView(empty);
    }

    public static void setEmptyViewForSLV(Context context, String txt, ViewGroup rootView, AbsListView listView) {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_lv_common_empty, null);
        ((TextView) emptyView.findViewById(R.id.tv_empty)).setText(txt);
        emptyView.setVisibility(View.GONE);
        rootView.addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }


    public static void setEmptyView(View emptyView, AbsListView listView) {
        emptyView.setVisibility(View.GONE);
//        ((ViewGroup) listView.getParent()).addView(emptyView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        listView.setEmptyView(emptyView);
    }

    public static String getSexStrigByIndex(String index) {
        String sexS = "男";
        switch (index) {
            case "0":
                sexS = "女";
                break;
            case "1":
                sexS = "男";
                break;
        }
        return sexS;

    }

    public static String getSexIndexByString(String string) {
        String sexI = "1";
        switch (string) {
            case "女":
                sexI = "0";
                break;
            case "男":
                sexI = "1";
                break;
        }
        return sexI;
    }


    public static int getColor(Context context, int res) {
        return context.getResources().getColor(res);
    }

    public static String getString(int res) {
        return context.getResources().getString(res);
    }

    public static Drawable getDrawable(Context context, int res) {
        return context.getResources().getDrawable(res);
    }

    public static Drawable getTextDrawable(int res) {
        Drawable drawable = context.getResources().getDrawable(res);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        return drawable;
    }

    public static String getUserCachePath() {
        if (null != user) {
            return G.STORAGEPATH + user.getUserCode() + "/";
        } else {
            return G.STORAGEPATH;
        }
    }

    public static boolean isDBInit(Context context) {
        if (null == App.liteOrm) {
            ToastUtil.showToastShort(context, "本地数据库未初始化成功");
            return false;
        } else {
            return true;
        }
    }

    public static String getFloatString2(float f) {
        return new DecimalFormat("0.00").format(f);
    }


    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     *
     * @return String
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return r + g + b;
    }

    /**
     * 获取登录Token
     */
    public static String getToken() {
        return SPUtil.getInstance().getString("token", "");
    }

    /**
     * 设置登录Token
     *
     * @param token
     */
    public static void setToken(String token) {
        SPUtil.getInstance().putString("token", token);
    }


    public static void setText(TextView tv, String txt) {
        if (AbStrUtil.isEmpty(txt)) {
            return;
        }
        tv.setText(txt);
    }

    public static void setText(EditText tv, String txt) {
        if (AbStrUtil.isEmpty(txt)) {
            return;
        }
        tv.setText(txt);
    }

    public static void setBackgroundDrawable(View v, int res) {
        v.setBackgroundDrawable(context.getResources().getDrawable(res));
    }

    public static void setBackgroundDrawable(Context context, View v, int res) {
        v.setBackgroundDrawable(context.getResources().getDrawable(res));
    }

    public static void setTextColor(TextView v, int res) {
        v.setTextColor(context.getResources().getColor(res));
    }

    public static void setImageDrawable(ImageView v, int res) {
        v.setImageDrawable(context.getResources().getDrawable(res));
    }


    public static <T> List<T> getRandomNumberItemFromList(List<T> list, int num) {
        if (list.size() <= num) {
            return list;
        }
        List<T> listR = new ArrayList<>();
        List<T> list2 = new ArrayList<>();
        list2.addAll(list);
        for (int i = 0; i < num; i++) {
            int r = new Random().nextInt(list2.size());
            if (null != list2.get(r)) {
                listR.add(list2.get(r));
                list2.remove(r);
            }
        }
        return listR;
    }

    // dialog
    public static CustomDialog getDialog(Context context) {
        if (dialog == null) {
            dialog = CustomDialog.instance(context);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public static void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public static void showDialog(Context context) {
        getDialog(context).show();
    }

    public static void setEtClearListener(final EditText et, final View view) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
            }
        });
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (AbStrUtil.isEmpty(et.getText().toString())) {
                    view.setVisibility(View.INVISIBLE);
                } else {
                    view.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public static List<String> getSampleList(int count) {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            items.add("");
        }
        return items;
    }

    public static String getFormatTimeString(String string) {

        return string.replace("T", " ");
    }

    public static List<String> getStringList(String[] strings) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            list.add(strings[i]);
        }
        return list;
    }

    public static String getGoodLogoFirst(String logos) {
        try {
            return getStringList(logos.split(",")).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static boolean getBoolean(int b) {
        return b == 1 ? true : false;
    }

    public static boolean isCouponPast(CouponM item) {
        String b = CommonUtils.getFormatTimeString(item.getUseBeginTime());
        String e = CommonUtils.getFormatTimeString(item.getUseEndTime());
        long now = new Date().getTime();
        long b1 = AbDateUtil.getDateByFormat(b, AbDateUtil.dateFormatYMDHMS).getTime();
        long e1 = AbDateUtil.getDateByFormat(e, AbDateUtil.dateFormatYMDHMS).getTime();
        if (now >= b1 && now <= e1) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isInConfines(MerchantM merchantM, LatLonPoint latLng) {
        try {
            LatLng shop_l = new LatLng(Double.valueOf(merchantM.getLat()), Double.valueOf(merchantM.getLng()));
            LatLng location_l = new LatLng(Double.valueOf(latLng.getLatitude()), Double.valueOf(latLng.getLongitude()));
            float distance = AMapUtils.calculateLineDistance(shop_l, location_l);
            float distruF = Float.valueOf(merchantM.getConfines());
            merchantM.setDistance(distance);
            if (distruF >= distance) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public static String getSDTimeDesrcDay(String time) {
        time = getFormatTimeString(time);
        String h = AbDateUtil.getStringByFormat(time, AbDateUtil.dateFormatHM);
        String bd = AbDateUtil.getStringByFormat(time, AbDateUtil.dateFormatYMD);
        String nd = AbDateUtil.getStringByFormat(new Date(), AbDateUtil.dateFormatYMD);
        if (bd.equals(nd)) {
            return "今天  " + h;
        } else {
            return bd + "  " + h;
        }
    }

    public static void intallApk(File t, Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.fromFile(t),
                "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
/*
    */
/**
 * @return 返回类型  String
 * @throws
 * @Title: getIpAddress
 * @Description: TODO(获取当前ip地址)
 *//*

    public static String getIpAddress(Context context) {
        String ip = null;
        ConnectivityManager conMann = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiNetworkInfo = conMann.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mobileNetworkInfo.isConnected()) {
            ip = getLocalIpAddress();
        }else if(wifiNetworkInfo.isConnected())
        {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipAddress = wifiInfo.getIpAddress();
            ip = intToIp(ipAddress);
        }
        return ip;

    }
    public static String getLocalIpAddress() {
        try {
            String ipv4;
            ArrayList<NetworkInterface>  nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface ni: nilist)
            {
                ArrayList<InetAddress>  ialist = Collections.list(ni.getInetAddresses());
                for (InetAddress address: ialist){
                    if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4=address.getHostAddress()))
                    {
                        return ipv4;
                    }
                }

            }

        } catch (SocketException ex) {
            Log.e("localip", ex.toString());
        }
        return null;
    }
    public static String intToIp(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }
*/

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }
    /**
     * 获取设备号
     */
    public static String getImei(Context context) {
        String imei = null;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(JpushUtil.class.getSimpleName(), e.getMessage());
        }
        return imei;
    }

    /**
     * 检查是否有定位的权限
     */
    public static boolean isHaveLocationPermi(final Context context) {
        PackageManager pm = context.getPackageManager();
        /*boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_FINE_LOCATION", context.getPackageName()));*/
        boolean permission = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED);
        boolean permission2 = (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_DENIED);
        if (permission) {
            DialogUtils.showDialog(context, "提示", "暂时未获取到定位权限，去设置？", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    if (which.name().equals(DialogAction.POSITIVE.name())) {
                        getAppDetailSettingIntent(context);
                    }
                }
            });
            return false;
        } else {
            return true;
        }
    }
    /**
     * 跳到系统应用详情界面
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }
}
