package com.ytxd.sppm.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kennyc.view.MultiStateView;
import com.ytxd.sppm.R;
import com.ytxd.sppm.event.SelectStaffEvent;
import com.ytxd.sppm.model.DeliveryStaffM;
import com.ytxd.sppm.presenter.DialogSelectDeliveryStaffPresenter;
import com.ytxd.sppm.ui.adapter.SelectDeliveryStaffLV;
import com.ytxd.sppm.util.ToastUtil;
import com.ytxd.sppm.view.ISelectDeliveryStaffView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * Created by XY on 2016/12/27.
 */

public class SelectDeliveryStaffDialog extends BaseDialogFragment<DialogSelectDeliveryStaffPresenter> implements ISelectDeliveryStaffView {


    @BindView(R.id.lv)
    ListView lv;
    SelectDeliveryStaffLV mAdapter;
    @BindView(R.id.msv)
    MultiStateView msv;
    DeliveryStaffM deliveryStaffM;
    int position=-1;


    @Override
    protected void initPresenter() {
        presenter = new DialogSelectDeliveryStaffPresenter(activity, this);
        presenter.init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_delivery_staff, container, false);
        ButterKnife.bind(this, view);
        initView();
        initData();
        return view;

    }

    private void initView() {
        position=getArguments().getInt("position");
        mAdapter = new SelectDeliveryStaffLV(new ArrayList<DeliveryStaffM>(), activity);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.setPositionS(position);
                deliveryStaffM = mAdapter.getItem(position);
            }
        });
    }

    private void initData() {
        presenter.getList();
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }


    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.v, R.id.tv_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.v:
                dismiss();
                break;
            case R.id.tv_ok:
                if (null == deliveryStaffM) {
                    ToastUtil.showToastShort(activity, "请选择配送员");
                }else{
                    if(position!=-1){
                        EventBus.getDefault().post(new SelectStaffEvent(position,deliveryStaffM));
                    }
                    dismiss();
                }
                break;
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeFailed() {
        showContent();
    }

    @Override
    public void lodeSuccess(List<DeliveryStaffM> items) {
        mAdapter.addItems(items, true);
        showContent();
    }

    private void showContent() {
        if (mAdapter.getCount() > 0) {
            msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        } else {
            msv.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }
}
