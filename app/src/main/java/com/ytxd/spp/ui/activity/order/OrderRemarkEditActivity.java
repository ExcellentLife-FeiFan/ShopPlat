package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.ui.adapter.TagOrderRemarkLV;
import com.ytxd.spp.ui.views.TagCloudLayout;
import com.ytxd.spp.util.AbStrUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderRemarkEditActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tag)
    TagCloudLayout tag;
    @BindView(R.id.et)
    EditText et;
    TagOrderRemarkLV tagOrderRemarkLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_remark_edit);
        ButterKnife.bind(this);
        getBar().initActionBar("订单备注", this);
        List<String> items = new ArrayList<>();
        items.add("注意保质期");
        items.add("送货前打电话");
        items.add("尽快送货");
        items.add("到了打电话");
        items.add("酒水要冰的");
        items.add("水果要新鲜");
        tagOrderRemarkLV = new TagOrderRemarkLV(items, this);
        tag.setAdapter(tagOrderRemarkLV);
        tag.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                tagOrderRemarkLV.setSelect(position,tagOrderRemarkLV.getItem(position));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        String remarks = et.getText().toString();
        if(!AbStrUtil.isEmpty(remarks)){
            Intent intent = new Intent();
            intent.putExtra("remark", remarks);
            setResult(EnsureOrderActivity.REMARKS,intent);
        }
        AppManager.getInstance().killActivity(this);
    }
}
