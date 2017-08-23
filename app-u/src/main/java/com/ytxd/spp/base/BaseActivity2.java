package com.ytxd.spp.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ytxd.spp.R;
import com.ytxd.spp.event.EmptyEvent;
import com.ytxd.spp.presenter.BasePresenter;
import com.ytxd.spp.ui.activity.main.MainActivity;
import com.ytxd.spp.ui.views.ActionBarView;
import com.ytxd.spp.ui.views.loadview.CustomDialog;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.AbWifiUtil;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by XY on 2016/11/2.
 */

public abstract class BaseActivity2<T extends BasePresenter> extends AppCompatActivity {
    protected AppCompatActivity activity;
    private CustomDialog dialog;//进度条
    protected ActionBarView actionBar;
    protected final List<View> viewsToAnimate = new ArrayList<>();
    protected String TAG = this.getClass().getSimpleName();
    protected T presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getInstance().addActivity(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        Glide.get(this).clearMemory();
        activity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }
        initPresenter();

    }

    protected void initPresenter() {

    }

    // --------------------------------------------------------------------------------------
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);

    }

    protected void startActivity(Class<?> cls, String... objs) {
        Intent intent = new Intent(this, cls);
        for (int i = 0; i < objs.length; i++) {
            intent.putExtra(objs[i], objs[++i]);
        }
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String[] keys, String[] values) {
        if (keys.length == values.length) {
            Intent intent = new Intent(this, cls);
            for (int i = 0; i < keys.length; i++) {
                intent.putExtra(keys[i], values[i]);
            }
            startActivity(intent);
        } else {
            LogUtils.e("keys和values的长度不一致！");
        }
    }

    protected void startActivityForResult(Class<?> cls, int requestCode, String[] keys, String[] values) {
        if (keys.length == values.length) {
            Intent intent = new Intent(this, cls);
            for (int i = 0; i < keys.length; i++) {
                intent.putExtra(keys[i], values[i]);
            }
            startActivityForResult(intent, requestCode);
        } else {
            LogUtils.e("keys和values的长度不一致！");
        }
    }

    protected void startActivity(Class<?> cls, String key, String value) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String key, Serializable value) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode, String key, String value) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivity(Class<?> cls, String key, Bundle data) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, data);
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode, String key, Bundle data) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(key, data);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(this, cls);
        startActivityForResult(intent, requestCode);
    }
    // --------------------------------------------------------------------------------------

    protected boolean isNetConnected() {
        return AbWifiUtil.isConnectivity(this);
    }

    protected void showToast(String txt) {
        ToastUtil.showToastShort(this, txt);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
//        getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide_from_left));
//        getWindow().setEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide_from_right));
        //        getWindow().setReenterTransition(slideTransition);
        //        getWindow().setReturnTransition(buildReturnTransition());
    }

    protected ActionBarView getBar() {
        actionBar = (ActionBarView) findViewById(R.id.actionBar);
        return actionBar;
    }


    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    // dialog
    public CustomDialog getDialog() {
        if (dialog == null) {
            dialog = CustomDialog.instance(this);
            dialog.setCancelable(true);
        }
        return dialog;
    }

    public void hideDialog() {
        if (dialog != null)
            dialog.hide();
    }

    public void showDialog() {
        getDialog().show();
    }

    public void dismissDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setText(TextView tv, String txt) {
        if (AbStrUtil.isEmpty(txt)) {
            return;
        }
        tv.setText(txt);
    }

    protected void setText(EditText tv, String txt) {
        if (AbStrUtil.isEmpty(txt)) {
            return;
        }
        tv.setText(txt);
    }

    protected void setBackgroundDrawable(View v, int res) {
        v.setBackgroundDrawable(getResources().getDrawable(res));
    }

    protected void setTextColor(TextView v, int res) {
        v.setTextColor(getResources().getColor(res));
    }

    protected void setImageDrawable(ImageView v, int res) {
        v.setImageDrawable(getResources().getDrawable(res));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getInstance().killActivity(activity);
    }

    protected void startEnterViewAnimate() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        startEnterViewAnimate();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != presenter) {
            presenter.release();
        }
        Glide.get(this).clearMemory();
        System.gc();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEvent(EmptyEvent event) {

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        startActivity(MainActivity.class);
        AppManager.getInstance().killActivity(activity);
    }
}
