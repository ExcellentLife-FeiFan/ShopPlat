package com.ytxd.spp.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.ui.adapter.GoodStandLV;
import com.ytxd.spp.ui.views.AnimShopButton;
import com.ytxd.spp.ui.views.TagCloudLayout;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ShoppingCartUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


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
    String merchantCode;
    GoodM stand;
    int type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_good_stand, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;

    }

    private void initData() {
        goodM = (GoodM) getArguments().getSerializable("data");
        merchantCode = getArguments().getString("merchantCode");
        type= getArguments().getInt("type");
        CommonUtils.setText(tvTitle, goodM.getGoodsTitle());
        stand=goodM.getGoods().get(0);
        btnAdd.setCount(ShoppingCartUtil.getLocalCartGoodCount(stand.getGoodsCode(), merchantCode));
        CommonUtils.setText(tvPrice, "¥"+stand.getXPrice());
        mAdapter = new GoodStandLV(goodM.getGoods(), activity);
        tag.setAdapter(mAdapter);
        tag.setItemClickListener(new TagCloudLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                mAdapter.setPositionS(position);
                stand = mAdapter.getItem(position);
                int count=ShoppingCartUtil.getLocalCartGoodCount(stand.getGoodsCode(), merchantCode);
                int cc=btnAdd.getCount();
                LogUtils.e(cc+"");
                btnAdd.setCount(count);
                btnAdd.invalidate();
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

    private void initView() {

        btnAdd.setOnAddDelListener(new AnimShopButton.IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                EventBus.getDefault().post(new GoodAddEvent(ivPlus,stand,type));
//                dismissAllowingStateLoss();
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                EventBus.getDefault().post(new GoodMinusEvent(stand,type));
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });

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
