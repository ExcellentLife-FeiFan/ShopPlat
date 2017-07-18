package com.ytxd.sppm.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.net.ApiResult;
import com.ytxd.sppm.net.Apis;
import com.ytxd.sppm.net.JsonCallback;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JpushUtil {
    public static final String KEY_APP_KEY = "JPUSH_APPKEY";
    public static String TAG = "JpushUtil";
    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;


    private static JpushUtil jpushSetUtil;
    private String userCode;

    public static JpushUtil getInstance() {
        if (jpushSetUtil == null) {
            jpushSetUtil = new JpushUtil();
        }
        return jpushSetUtil;
    }


    public void setTag() {
        String tag = getImei(App.context, "");
        // 检查 tag 的有效性
        if (TextUtils.isEmpty(tag)) {
            Log.e(TAG, "Tag is empty!");
            return;
        }
        // ","隔开的多个 转换成 Set
        String[] sArray = tag.split(",");
        Set<String> tagSet = new LinkedHashSet<>();
        for (String sTagItme : sArray) {
            if (!isValidTagAndAlias(sTagItme)) {
                Log.e(TAG, "Tag is valid!");
                return;
            }
            tagSet.add(sTagItme);
        }
        //调用JPush API设置Tag
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
    }

    public void setAlias(String userCode) {
        this.userCode=userCode;
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, getImei(App.context, "")));
    }

    public void setAliasNull(String userCode) {
        this.userCode=userCode;
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));
    }



    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(App.context, (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    Log.d(TAG, "Set tags in handler.");
                    JPushInterface.setAliasAndTags(App.context, null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    setJpushAlias(alias);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (AbWifiUtil.isConnectivity(App.context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

            LogUtils.e(logs);
        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (AbWifiUtil.isConnectivity(App.context)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            LogUtils.e(logs);
        }

    };

    private void setJpushAlias(String alias) {
        if (!AbWifiUtil.isConnectivity(App.context)) {
            ToastUtil.showToastShort(App.context, App.context.getResources().getString(R.string.net_wrong));
            return;
        }
        OkGo.<ApiResult<Object>>get(Apis.UpdateCSAlias)//
                .params("SupermarketCode", userCode)
                .params("TSAlias", alias)
                .params("AndroidOrIos", "1")
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                        } else {
                            ToastUtil.showToastShort(App.context, "设置极光推送别名标识失败");
                        }

                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        super.onError(response);
                        ToastUtil.showToastShort(App.context, "设置极光推送别名标识失败");
                    }
                });

    }


    // 校验Tag Alias 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    // 取得AppKey
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(KEY_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (NameNotFoundException e) {

        }
        return appKey;
    }


    public static String getImei(Context context, String imei) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(JpushUtil.class.getSimpleName(), e.getMessage());
        }
        return imei;
    }

    public static String getDeviceId(Context context) {
        String deviceId = JPushInterface.getUdid(context);
        return deviceId;
    }
}
