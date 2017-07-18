package com.ytxd.sppm.receiver;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.ytxd.sppm.R;
import com.ytxd.sppm.event.MainNotificationEvent;
import com.ytxd.sppm.model.JpushNotifyM;
import com.ytxd.sppm.ui.activity.main.MainActivity;
import com.ytxd.sppm.util.AbStrUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JpushReceiver extends BroadcastReceiver {
    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
            //打开自定义的Activity
            Intent i = new Intent(context, MainActivity.class);
//            i.putExtras(bundle);
            //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);
            EventBus.getDefault().post(new MainNotificationEvent(bundle));
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Log.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {

        JpushNotifyM jpushNotifyM = new JpushNotifyM();
        jpushNotifyM.setALERT(bundle.getString(JPushInterface.EXTRA_ALERT));
        jpushNotifyM.setEXTRA(bundle.getString(JPushInterface.EXTRA_EXTRA));
        jpushNotifyM.setNOTIFICATION_ID(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_ID));
        jpushNotifyM.setALERT_TYPE(bundle.getString(JPushInterface.EXTRA_ALERT_TYPE));
        jpushNotifyM.setNOTIFICATION_CONTENT_TITLE(bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
        jpushNotifyM.setMSG_ID(bundle.getString(JPushInterface.EXTRA_MSG_ID));
        jpushNotifyM.setMESSAGE(bundle.getString(JPushInterface.EXTRA_MESSAGE));

        String content = jpushNotifyM.getMESSAGE();
        if (!AbStrUtil.isEmpty(jpushNotifyM.getEXTRA())) {
            content = content + ": " + jpushNotifyM.getEXTRA();
        }
        sendNotify(context, content);
    }

    static final int NOTIFICATION_ID = 0x123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotify(Context context, String content) {
        NotificationManager nm = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        // 创建一个启动其他Activity的Intent
        Intent intent = new Intent(context
                , MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(
                context, 0, intent, 0);
        Notification notify = new Notification.Builder(context)
                // 设置打开该通知，该通知自动消失
                .setAutoCancel(true)
                // 设置显示在状态栏的通知提示信息
                .setTicker("有新消息")
                // 设置通知的图标
                .setSmallIcon(R.mipmap.ic_launcher)
                // 设置通知内容的标题
                .setContentTitle("一条新消息")
                // 设置通知内容
                .setContentText(content)
                // 设置使用系统默认的声音、默认LED灯
                .setDefaults(Notification.DEFAULT_SOUND
                        | Notification.DEFAULT_LIGHTS)
                // 设置通知的自定义声音
//                .setSound(Uri.parse("android.resource://org.crazyit.ui/"
//                        + R.raw.msg))
                .setWhen(System.currentTimeMillis())
                // 设改通知将要启动程序的Intent
                .setContentIntent(pi)  // ①
                .build();
        // 发送通知
        nm.notify(NOTIFICATION_ID, notify);
    }
}
