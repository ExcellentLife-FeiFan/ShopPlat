package com.ytxd.sppm.ui.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.flyco.systembar.SystemBarHelper;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.base.BaseActivity;
import com.ytxd.sppm.base.G;
import com.ytxd.sppm.event.HomeOrderRefreshEvent;
import com.ytxd.sppm.event.MainNotificationEvent;
import com.ytxd.sppm.event.SetOrderFMEvent;
import com.ytxd.sppm.presenter.MainPresenter;
import com.ytxd.sppm.ui.fm.HomeFM1;
import com.ytxd.sppm.ui.fm.HomeFM2;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.JpushUtil;
import com.ytxd.sppm.util.SPUtil;
import com.ytxd.sppm.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainView{

    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomBar;
    private HomeFM1 fm1;
    private HomeFM2 fm2;

    Fragment currentFragment;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initPresenter() {
        presenter = new MainPresenter(this, this);
        presenter.init();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SystemBarHelper.tintStatusBar(this, CommonUtils.getColor(this, R.color.colorPrimary), 0.3f);
        presenter.setBlueToothAdapter(this);
        if (null == savedInstanceState) {
            fm1 = new HomeFM1();
            fm2 = new HomeFM2();
            switchFragment(fm1);
        }
        String rid = JPushInterface.getRegistrationID(getApplicationContext());
        if (!rid.isEmpty()) {
        } else {
            Toast.makeText(this, "Get registration fail, JPush init failed!", Toast.LENGTH_SHORT).show();
        }

        if (SPUtil.getInstance().getBoolean( G.IS_PUSH, true)) {
            JPushInterface.resumePush(getApplicationContext());
        } else {
            JPushInterface.stopPush(getApplicationContext());
        }
        if(CommonUtils.isLogined2()){
            JpushUtil.getInstance().setAlias(App.user.getSupermarketCode());
        }

        if (CommonUtils.isLogined2()) {
            App.initDataBase(this);
        }

        // Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.home_b_tab_0, R.drawable.ic_order, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.home_b_tab_1, R.drawable.ic_mine, R.color.colorPrimary);

// Add items
        bottomBar.addItem(item1);
        bottomBar.addItem(item2);

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
                    switchFragment(fm1);
                } else if (position == 1) {
                    switchFragment(fm2);
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
        trans.commitAllowingStateLoss();
        currentFragment = fragment;
    }


    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
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
            EventBus.getDefault().post(new HomeOrderRefreshEvent());
            EventBus.getDefault().post(new SetOrderFMEvent(0));
            bottomBar.setCurrentItem(0);
        }

    }

    @Override
    public void init() {

    }
}
