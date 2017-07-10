package com.ytxd.spp.ui.activity.mine.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.util.AbStrUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNicknameActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et)
    EditText et;
    private String nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nickname);
        ButterKnife.bind(this);
        getBar().initActionBar("修改昵称", "", "保存", R.drawable.ic_back_white, -1, this);
        nickName = getIntent().getStringExtra("data");
        if (!AbStrUtil.isEmpty(nickName)) {
            et.setText(nickName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
            case R.id.tv_right:
                if(AbStrUtil.isEmpty(et.getText().toString())){
                   showToast("昵称不能为空");
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("data",et.getText().toString());
                    setResult(1,intent);
                    AppManager.getInstance().killActivity(activity);
                }
                break;
        }

    }
}
