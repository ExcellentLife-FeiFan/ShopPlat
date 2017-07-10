package com.ytxd.spp.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ytxd.spp.R;
import com.ytxd.spp.base.App;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.event.RefreshUserData;
import com.ytxd.spp.presenter.AccountPresenter;
import com.ytxd.spp.ui.activity.login.FindPwdActivity;
import com.ytxd.spp.ui.activity.mine.account.BindNewPhone1Activity;
import com.ytxd.spp.ui.activity.mine.account.EditNicknameActivity;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.view.IAccountView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class AccountActivity extends BaseActivity<AccountPresenter> implements View.OnClickListener, IAccountView {

    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_bind_phone)
    TextView tvBindPhone;
    @BindView(R.id.tv_bind_qq)
    TextView tvBindQq;
    @BindView(R.id.tv_bind_weixin)
    TextView tvBindWeixin;
    @BindView(R.id.tv_login_pwd)
    TextView tvLoginPwd;
    @BindView(R.id.tv_pay_pwd)
    TextView tvPayPwd;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    private String nickname;

    @Override
    protected void initPresenter() {
        presenter = new AccountPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        CommonUtils.setText(tvNickname, App.user.getNickName());
        ImageLoadUtil.setImageNP2(App.user.getTitlePath(), civ, this);
        CommonUtils.setText(tvBindPhone, App.user.getPhone());
        getBar().initActionBar("账户与安全", this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.rl_icon, R.id.rl_name, R.id.rl_phone, R.id.rl_qq, R.id.rl_weixin, R.id.rl_loginpwd, R.id.rl_paypwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_icon:
                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setPreviewEnabled(false)
                        .setSelected(selectedPhotos)
                        .start(activity);
                break;
            case R.id.rl_name:
                startActivityForResult(EditNicknameActivity.class, 1, "data", tvNickname.getText().toString());
                break;
            case R.id.rl_phone:
                startActivity(BindNewPhone1Activity.class);
                break;
            case R.id.rl_qq:
                break;
            case R.id.rl_weixin:
                break;
            case R.id.rl_loginpwd:
                startActivity(FindPwdActivity.class);
                break;
            case R.id.rl_paypwd:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }

            if (selectedPhotos.size() > 0) {
                presenter.upLoadImg(new File(selectedPhotos.get(0)));
            }

        }
        if (resultCode == 1) {
            nickname = data.getStringExtra("data");
            presenter.modifyNickName(nickname);
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void showDialogs() {
        showDialog();

    }

    @Override
    public void dismissDialogs() {
        dismissDialog();
    }

    @Override
    public void changeIconSuccess() {
        Glide.with(this)
                .load(new File(selectedPhotos.get(0)))
                .centerCrop()
                .thumbnail(0.5f)
                .into(civ);
        EventBus.getDefault().post(new RefreshUserData());
    }

    @Override
    public void changeNicknameSuccess() {
        CommonUtils.setText(tvNickname, nickname);
        App.user.setNickName(nickname);
        EventBus.getDefault().post(new RefreshUserData());
    }
}
