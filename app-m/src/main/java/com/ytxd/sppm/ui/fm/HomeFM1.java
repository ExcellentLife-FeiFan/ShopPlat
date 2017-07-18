package com.ytxd.sppm.ui.fm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.BaseFragment;
import com.ytxd.sppm.event.SetOrderFMEvent;
import com.ytxd.sppm.ui.fm.order.OrderFM1;
import com.ytxd.sppm.ui.fm.order.OrderFM2;
import com.ytxd.sppm.ui.fm.order.OrderFM3;
import com.ytxd.sppm.ui.fm.order.OrderFM4;
import com.ytxd.sppm.ui.fm.order.OrderFM5;
import com.ytxd.sppm.ui.fm.order.OrderFM6;
import com.ytxd.sppm.ui.fm.order.OrderFM7;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM1 extends BaseFragment {
    @BindView(R.id.tab)
    SlidingTabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    private Unbinder unbinder;

    private String[] titles = new String[]{"全部", "待接单", "待配送", "正在配送", "已完成", "已取消", "已退款"};


/*    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(activity, this);
        presenter.init();
    }*/


    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_1;
    }

    @Override
    public void initView() {
        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager());
        vp.setAdapter(adapter);
        vp.setOffscreenPageLimit(7);
        tab.setViewPager(vp, titles);
    }

    @Override
    protected void initData() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {


        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                OrderFM1 fragment1 = new OrderFM1();
                return fragment1;
            } else if (position == 1) {
                OrderFM2 fragment2 = new OrderFM2();
                return fragment2;
            } else if (position == 2) {
                OrderFM3 fragment3 = new OrderFM3();
                return fragment3;
            } else if (position == 3) {
                OrderFM4 fragment4 = new OrderFM4();
                return fragment4;
            } else if (position == 4) {
                OrderFM5 fragment5 = new OrderFM5();
                return fragment5;
            } else if (position == 5) {
                OrderFM6 fragment6 = new OrderFM6();
                return fragment6;
            } else if (position == 6) {
                OrderFM7 fragment7 = new OrderFM7();
                return fragment7;
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }

    public void onEvent(SetOrderFMEvent event) {
        tab.setCurrentTab(event.position,true);
    }
}