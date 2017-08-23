package com.ytxd.spp.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ytxd.spp.R;
import com.ytxd.spp.base.G;
import com.ytxd.spp.event.WeChatPaySuccessEvent;
import com.ytxd.spp.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "com.ytxd.spp.wxapi.WXPayEntryActivity";
    @BindView(R.id.tv_result)
    TextView tvResult;

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wechat_pay_result);
        ButterKnife.bind(this);
        api = WXAPIFactory.createWXAPI(this, G.WeChatAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == 0) {
            EventBus.getDefault().post(new WeChatPaySuccessEvent(true));
            tvResult.setText("微信支付成功");
        } else {
            EventBus.getDefault().post(new WeChatPaySuccessEvent(false));
            tvResult.setText("微信支付失败：" + resp.errCode);
        }
        tvResult.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },1500);
        LogUtils.d(TAG, "onPayFinish, errCode = " + resp.errCode);
    }

  /*  @OnClick(R.id.btn_ok)
    public void onClick() {
        finish();
    }*/
}