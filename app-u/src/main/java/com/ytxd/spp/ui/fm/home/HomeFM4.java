package com.ytxd.spp.ui.fm.home;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.BaseFragment;
import com.ytxd.spp.event.RefreshLoginEvent;
import com.ytxd.spp.event.RefreshUserData;
import com.ytxd.spp.ui.activity.mine.AccountActivity;
import com.ytxd.spp.ui.activity.mine.AddressManaActivity;
import com.ytxd.spp.ui.activity.mine.DiscountCouponActivity;
import com.ytxd.spp.ui.activity.mine.SettingsActivity;
import com.ytxd.spp.ui.dialog.InvitecodeShowDialog;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM4 extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;

    @Override
    public int getLayoutRes() {
        return R.layout.fm_home_4;
    }

    @Override
    public void initView() {
        if (CommonUtils.isLogined2()) {
            CommonUtils.setText(tvInviteCode, "我的邀请码：    "+App.user.getInvitationCode());
            setUserData();
        } else {
            tvNickname.setText("立即登录");
            tvPhone.setText("登录后享受更多特权优惠");
        }
    }

    private void setUserData() {
        ImageLoadUtil.setImageNP2(App.user.getTitlePath(), civ, activity);
        CommonUtils.setText(tvNickname, App.user.getNickName());
        CommonUtils.setText(tvPhone, App.user.getPhone());

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

    @OnClick({R.id.rl_v_right, R.id.civ, R.id.rl_coupon, R.id.rl_address_mana, R.id.rl_commnet, R.id.rl_invitecode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_v_right:
                startActivity(SettingsActivity.class);
                break;
            case R.id.civ:
                if (CommonUtils.isLogined(activity)) {
                    startActivity(AccountActivity.class);
                }
                break;
            case R.id.rl_address_mana:
                if (CommonUtils.isLogined(activity)) {
                    startActivity(AddressManaActivity.class);
                }
                break;
            case R.id.rl_commnet:

                break;
            case R.id.rl_coupon:
                if (CommonUtils.isLogined(activity)) {
                    startActivity(DiscountCouponActivity.class);
                }
                break;
            case R.id.rl_invitecode:
                if (CommonUtils.isLogined(activity)) {
                    InvitecodeShowDialog invitecodeShowDialog = new InvitecodeShowDialog();
                    invitecodeShowDialog.show(activity.getFragmentManager(),"InvitecodeShowDialog");
                }
                break;
        }
    }


    public void onEvent(RefreshUserData event) {
        setUserData();
    }

    public void onEvent(RefreshLoginEvent event) {
        if (CommonUtils.isLogined2()) {
            setUserData();
        }
    }
}
