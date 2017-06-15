package com.ytxd.spp.ui.views.pop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.lib.AnimShopButton;
import com.ytxd.spp.R;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.ui.adapter.GoodStandLV;
import com.ytxd.spp.ui.views.TagCloudLayout;
import com.ytxd.spp.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by XY on 2016/12/27.
 */

public class SelectGoodStandDialog extends BaseDialogFragment {

    @BindView(R.id.v)
    View v;
    @BindView(R.id.tag)
    TagCloudLayout tag;
    @BindView(R.id.iv_plus)
    ImageView ivPlus;
    @BindView(R.id.btnAdd)
    AnimShopButton btnAdd;
    GoodStandLV mAdapter;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    GoodM goodM;

    GoodM stand;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_good_stand, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        return view;

    }

    private void initData() {
        goodM = (GoodM) getArguments().getSerializable("data");
        CommonUtils.setText(tvTitle, goodM.getGoodsTitle());
        stand=goodM.getGoods().get(0);
        CommonUtils.setText(tvPrice, "¥"+stand.getXPrice());
        mAdapter = new GoodStandLV(goodM.getGoods(), activity);
        tag.setAdapter(mAdapter);
        tag.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                mAdapter.setPositionS(position);
                stand = mAdapter.getItem(position);
                CommonUtils.setText(tvPrice, "¥"+stand.getXPrice());
            }
        });

    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void initView(View view) {

    }

    @OnClick(R.id.v)
    public void onViewClicked() {
        this.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
