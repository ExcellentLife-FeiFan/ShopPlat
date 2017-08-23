package com.ytxd.spp.ui.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.flyco.systembar.SystemBarHelper;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.base.G;
import com.ytxd.spp.event.MainNotificationEvent;
import com.ytxd.spp.event.RefreshOrderListEvent;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.model.UserM;
import com.ytxd.spp.presenter.MainPresenter;
import com.ytxd.spp.ui.activity.order.OrderDetailActivity;
import com.ytxd.spp.ui.dialog.HomeCouponDialog;
import com.ytxd.spp.ui.fm.home.HomeFM1;
import com.ytxd.spp.ui.fm.home.HomeFM3;
import com.ytxd.spp.ui.fm.home.HomeFM4;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.JpushUtil;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.SPUtil;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomBar;
    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    HomeFM1 fm1;
    //    HomeFM2 fm2;
    HomeFM3 fm3;
    HomeFM4 fm4;
    Fragment currentFragment;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SystemBarHelper.tintStatusBar(this, CommonUtils.getColor(this, R.color.colorPrimary), 0.3f);
        //挡Activity意外退出时再返回时，Activity会重新启动，但savedInstanceState不为空
        // getSupportFragmentManager()里的Fragment也没有隐藏去掉,所以为了不使界面冲突需隐藏移除之前残留的Fragment
        if (null != savedInstanceState) {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
            Fragment f = getSupportFragmentManager().findFragmentByTag(HomeFM1.class.getName());
            if (null != f) {
                trans.hide(f);
                trans.remove(f);
            }
            Fragment f2 = getSupportFragmentManager().findFragmentByTag(HomeFM3.class.getName());
            if (null != f2) {
                trans.hide(f2);
                trans.remove(f2);
            }
            Fragment f3 = getSupportFragmentManager().findFragmentByTag(HomeFM4.class.getName());
            if (null != f3) {
                trans.hide(f3);
                trans.remove(f3);
            }
            trans.commitAllowingStateLoss();

            UserM userM= (UserM) savedInstanceState.getSerializable("user");
            if(null!=userM){
                App.user = userM;
            }
            App.initDataBase(this);
        }else{

        }

        fm1 = new HomeFM1();
//            fm2 = new HomeFM2();
        fm3 = new HomeFM3();
        fm4 = new HomeFM4();
        switchFragment(fm1);

        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        if (!rid.isEmpty()) {
        } else {
            Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
        }

        if (SPUtil.getInstance().getBoolean(G.IS_PUSH, true)) {
            JPushInterface.resumePush(getApplicationContext());
        } else {
            JPushInterface.stopPush(getApplicationContext());
        }

        if (CommonUtils.isLogined2()) {
            presenter.getCouponList();
            JpushUtil.getInstance().setAlias(App.user.getUserCode());
        }
        App.initDataBase(this);
        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.home_b_tab_0, R.drawable.ic_home, R.color.colorPrimary);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.home_b_tab_1, R.drawable.ic_find, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.home_b_tab_2, R.drawable.ic_order, R.color.colorPrimary);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.home_b_tab_3, R.drawable.ic_mine, R.color.colorPrimary);

// Add items
        bottomBar.addItem(item1);
//        bottomBar.addItem(item2);
        bottomBar.addItem(item3);
        bottomBar.addItem(item4);

        bottomBar.setBehaviorTranslationEnabled(false);
// Manage titles
        bottomBar.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        // Set background color
        bottomBar.setDefaultBackgroundColor(Color.parseColor("#FFFFFF"));
        // Change colors
        bottomBar.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomBar.setInactiveColor(getResources().getColor(R.color.tab_unselect));
        bottomBar.setForceTint(true);
        bottomBar.setTranslucentNavigationEnabled(true);
        // Use colored navigation with circle reveal effect
        bottomBar.setColored(false);
        // Add or remove notification for each item
//        bottomBar.setNotification("1", 0);
        // OR
        AHNotification notification = new AHNotification.Builder()
                .setText("1")
                .setBackgroundColor(ContextCompat.getColor(this, R.color.yellow))
                .setTextColor(ContextCompat.getColor(this, R.color.black_overlay))
                .build();
//        bottomBar.setNotification(notification, 2);

        // Set listeners
        bottomBar.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                FragmentManager fm = getSupportFragmentManager();
                // 开启Fragment事务
                FragmentTransaction transaction = fm.beginTransaction();
                if (position == 0) {
                    if (null == fm1) {
                        fm1 = new HomeFM1();
                    }
                    switchFragment(fm1);
                } /*else if (position == 1) {
                    switchFragment(fm2);
                }*/ else if (position == 1) {
                  /*  if(CommonUtils.isLogined(activity)){
                        switchFragment(fm3);
                    }else{
                        return false;
                    }*/
                    if (null == fm3) {
                        fm3 = new HomeFM3();
                    }
                    switchFragment(fm3);

                } else if (position == 2) {
                   /* if(CommonUtils.isLogined(activity)){
                        switchFragment(fm4);
                    }else{
                        return false;
                    }*/
                    if (null == fm4) {
                        fm4 = new HomeFM4();
                    }
                    switchFragment(fm4);
                }
                transaction.commitAllowingStateLoss();
                return true;
            }
        });
        bottomBar.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override
            public void onPositionChange(int y) {
                // Manage the new y positi5gon
            }
        });
        bottomBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.checkForUpdateApp(activity);
            }
        }, 2000);


    }

    /**
     * 切换fragment
     */
    public void switchFragment(Fragment fragment) {
        boolean isFound = false;
        Fragment usedFragment = null;
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (fragments.size() > 0) {
            for (Fragment f : fragments) {
                trans.hide(f);
                if (f.getClass().getName().equals(fragment.getClass().getName())) {
                    isFound = true;
                    usedFragment = f;
                }
            }
            if (isFound) {
                trans.show(usedFragment);
            } else {
                trans.add(R.id.contentContainer, fragment, fragment.getClass().getName());
                fragments.add(fragment);
            }
        } else {
            if (null == fragment) {
                return;
            }
            trans.add(R.id.contentContainer, fragment, fragment.getClass().getName());
            fragments.add(fragment);
        }
        trans.commitNow();
        currentFragment = fragment;
    }


    private long nowTime;

    @Override
    public void onBackPressed() {
        ToastUtil.showToastShort(activity, "再按一次退出程序");
        if (System.currentTimeMillis() - nowTime < 2000) {
            AppManager.getInstance().AppExit(this);
        } else {
            nowTime = System.currentTimeMillis();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if (null != data) {
        }
    }

    public void onEvent(MainNotificationEvent event) {
        if (null != event.data) {
            JSONObject extra = JSONObject.parseObject(event.data.getString(JPushInterface.EXTRA_EXTRA));
            String state = extra.getString("State");
            switch (state) {
                case "200":
                case "201":
                case "202":
                    startActivity(OrderDetailActivity.class, "orderCode", extra.getString("OrderCode"));
                    EventBus.getDefault().post(new RefreshOrderListEvent());
                    break;
                case "900":
                    startActivity(WebActivity.class, "url", extra.getString("Url"));
                    break;
            }
        }

    }

    @Override
    public void init() {

    }

    @Override
    public void lodeCouponSuccess(List<CouponM> items) {
        if (items.size() > 0) {
            ArrayList<CouponM> unread = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                if (!CommonUtils.getBoolean(items.get(i).getIsRead())) {
                    unread.add(items.get(i));
                }
            }
            if (unread.size() > 0) {
                HomeCouponDialog homeCouponDialog = new HomeCouponDialog();
                Bundle data = new Bundle();
                data.putSerializable("data", unread);
                homeCouponDialog.setArguments(data);
                homeCouponDialog.show(getFragmentManager(), "HomeCouponDialog");
                presenter.setCouponRead();
            }
        }
    }

    @Override
    public void lodeCouponFailed() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (null != App.user) {
            outState.putSerializable("user", App.user);
        }
        super.onSaveInstanceState(outState);
        LogUtils.e("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e("onPause");
    }
}
