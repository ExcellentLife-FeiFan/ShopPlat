package com.ytxd.spp.ui.activity.main;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.util.AbStrUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.web)
    WebView web;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        getBar().initActionBar("其他", this);
        url=getIntent().getStringExtra("url");
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl("");
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                getBar().initActionBar(web.getTitle(), WebActivity.this);
            }
        });
        WebSettings settings = web.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        if(!AbStrUtil.isEmpty(url)){
            web.loadUrl(url);
        }else{
            showToast("网址出错");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }
}
