package com.ytxd.sppm.ui.fm;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.BaseFragment;
import com.ytxd.sppm.ui.activity.mine.SettingsActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM2 extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private Unbinder unbinder;


/*    @Override
    protected void initPresenter() {
        presenter = new HomePresenter(activity, this);
        presenter.init();
    }*/


    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_2;
    }

    @Override
    public void initView() {

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


    @Override
    public void onRefresh() {


    }

    @Override
    public void onLoadMoreRequested() {
        /*refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.loadMoreEnd(true);
            }
        }, 100);*/
    }

    @OnClick({R.id.rl_v_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_v_right:
                startActivity(SettingsActivity.class);
                break;
        }
    }
}