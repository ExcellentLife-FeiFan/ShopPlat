package com.ytxd.sppm.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytxd.sppm.event.EmptyEvent;
import com.ytxd.sppm.presenter.BasePresenter;
import com.ytxd.sppm.util.AbWifiUtil;
import com.ytxd.sppm.util.LogUtils;
import com.ytxd.sppm.util.SPUtil;
import com.ytxd.sppm.util.ToastUtil;

import java.io.Serializable;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by XY on 2016/11/2.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    public AppCompatActivity activity;
    private View mLayoutView;

    private boolean isVisible;                  //是否可见状态
    private boolean isPrepared;                 //标志位，View已经初始化完成。
    private boolean isFirstLoad = true;

    protected T presenter;

    /**
     * 初始化布局
     */
    public abstract int getLayoutRes();

    /**
     * 初始化视图
     */
    public abstract void initView();


    protected void initData() {
    }

    ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initPresenter();
    }

    protected void initPresenter() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mLayoutView = getCreateView(inflater, container);
        ButterKnife.bind(this, mLayoutView);
        initView();
        initData();
        return mLayoutView;
    }

    /**
     * 获取Fragment布局文件的View
     *
     * @param inflater
     * @param container
     * @return
     */
    private View getCreateView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    /**
     * 获取当前Fragment状态
     *
     * @return true为正常 false为未加载或正在删除
     */
    private boolean getStatus() {
        return (isAdded() && !isRemoving());
    }

    /**
     * 获取Activity
     *
     * @return
     */
    public AppCompatActivity getBaseActivity() {
        if (activity == null) {
            activity = (BaseActivity) getActivity();
        }
        return activity;
    }


   /* */

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *//*
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }*/
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;
        initData();
    }

    protected boolean isNetConnected() {
        return AbWifiUtil.isConnectivity(getActivity());
    }

    protected void showToast(String txt) {
        ToastUtil.showToastShort(getActivity(), txt);
    }

    public void onEvent(EmptyEvent event) {

    }


    // --------------------------------------------------------------------------------------


    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String... objs) {
        Intent intent = new Intent(activity, cls);
        for (int i = 0; i < objs.length; i++) {
            intent.putExtra(objs[i], objs[++i]);
        }
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String[] keys, String[] values) {
        if (keys.length == values.length) {
            Intent intent = new Intent(activity, cls);
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
            Intent intent = new Intent(activity, cls);
            for (int i = 0; i < keys.length; i++) {
                intent.putExtra(keys[i], values[i]);
            }
            startActivityForResult(intent, requestCode);
        } else {
            LogUtils.e("keys和values的长度不一致！");
        }
    }

    protected void startActivity(Class<?> cls, String key, String value) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String key, Serializable value) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key, value);
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode, String key, String value) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key, value);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivity(Class<?> cls, String key, Bundle data) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key, data);
        startActivity(intent);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode, String key, Bundle data) {
        Intent intent = new Intent(activity, cls);
        intent.putExtra(key, data);
        startActivityForResult(intent, requestCode);
    }

    protected void startActivityForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent(activity, cls);
        startActivityForResult(intent, requestCode);
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

    /**
     * 获取登录Token
     */
    public String getToken() {
        return SPUtil.getInstance().getString("token", "");
    }

    /**
     * 设置登录Token
     *
     * @param token
     */
    public void setToken(String token) {
        SPUtil.getInstance().putString("token", token);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (null != presenter) {
            presenter.release();
        }
    }
}
