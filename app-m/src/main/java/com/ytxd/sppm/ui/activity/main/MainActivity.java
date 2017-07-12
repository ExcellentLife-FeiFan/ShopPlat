package com.ytxd.sppm.ui.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.flyco.systembar.SystemBarHelper;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.base.BaseActivity;
import com.ytxd.sppm.ui.fm.HomeFM1;
import com.ytxd.sppm.ui.fm.HomeFM2;
import com.ytxd.sppm.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomBar;
    private HomeFM1 fm1;
    private HomeFM2 fm2;

    Fragment currentFragment;
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        SystemBarHelper.tintStatusBar(this, CommonUtils.getColor(this, R.color.colorPrimary), 0.3f);
        if (null == savedInstanceState) {
            fm1 = new HomeFM1();
            fm2 = new HomeFM2();
            switchFragment(fm1);
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
}
