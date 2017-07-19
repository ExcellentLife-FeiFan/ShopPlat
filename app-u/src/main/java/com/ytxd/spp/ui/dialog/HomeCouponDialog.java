package com.ytxd.spp.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.ui.adapter.HomeCouponLV;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by XY on 2016/12/27.
 */

public class HomeCouponDialog extends BaseDialogFragment {

    @BindView(R.id.v)
    View v;
    @BindView(R.id.iv_cha)
    ImageView ivCha;
    @BindView(R.id.lv)
    ListView lv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_home_coupon, container, false);
        ButterKnife.bind(this, view);
        setCancelable(false);
        initView();
        initData();
        return view;

    }

    private void initData() {


    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    private void initView() {
        ivCha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        HomeCouponLV adapter = new HomeCouponLV((ArrayList<CouponM>) getArguments().getSerializable("data"), activity);
        lv.setAdapter(adapter);
    }

    @OnClick(R.id.v)
    public void onViewClicked() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
