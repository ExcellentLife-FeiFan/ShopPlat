package com.ytxd.sppm.ui.fm;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qiushui.blurredview.BlurredView;
import com.ytxd.sppm.R;
import com.ytxd.sppm.base.App;
import com.ytxd.sppm.base.BaseFragment;
import com.ytxd.sppm.net.Apis;
import com.ytxd.sppm.ui.activity.mine.SettingsActivity;
import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.CommonUtils;
import com.ytxd.sppm.util.ImageLoadUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by apple on 2017/3/29.
 */

public class HomeFM2 extends BaseFragment {
    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_state)
    TextView tvState;
    private Unbinder unbinder;
    @BindView(R.id.blurredview)
    BlurredView blurredview;


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
        ImageLoadUtil.setImageNP(App.user.getLogoUrl(),civ,activity);
        if (!AbStrUtil.isEmpty(App.user.getHJUrl()))
        {
            Glide.with(this)
                    .load(Apis.AddPATH(App.user.getHJUrl()))
                    .error(R.color.img_bg)
                    .placeholder(R.color.img_bg)
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            blurredview.setBlurredLevel(95);
                            blurredview.setBlurredImg(resource);
                        }
                    });
        } else if (!AbStrUtil.isEmpty(App.user.getLogoUrl())) {
            Glide.with(this)
                    .load(Apis.AddPATH(App.user.getLogoUrl()))
                    .error(R.color.img_bg)
                    .placeholder(R.color.img_bg)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存全尺寸
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            blurredview.setBlurredLevel(95);
                            blurredview.setBlurredImg(resource);
                        }
                    });
        }
        CommonUtils.setText(tvName,App.user.getName());
        CommonUtils.setText(tvState,"正在营业");
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


    @OnClick({R.id.rl_v_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_v_right:
                startActivity(SettingsActivity.class);
                break;
        }
    }
}