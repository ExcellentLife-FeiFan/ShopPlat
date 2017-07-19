package com.ytxd.spp.ui.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ytxd.spp.R;
import com.ytxd.spp.event.SelectArriveTimeEvent;
import com.ytxd.spp.model.ArriveTimeM;
import com.ytxd.spp.ui.adapter.SelectSendTimeLV;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


/**
 * Created by XY on 2016/12/27.
 */

public class SelectSendTimeDialog extends BaseDialogFragment {
    @BindView(R.id.lv)
    ListView lv;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_select_send_time, container, false);
        ButterKnife.bind(this, view);
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
        int minb=getArguments().getInt("pst");
        long time=new Date().getTime();
        List<ArriveTimeM> items = new ArrayList<>();
        items.add(new ArriveTimeM(minb,time));
        items.add(new ArriveTimeM(minb+15,time));
        items.add(new ArriveTimeM(minb+30,time));
        items.add(new ArriveTimeM(minb+45,time));
        items.add(new ArriveTimeM(minb+60,time));
        items.add(new ArriveTimeM(minb+75,time));
        items.add(new ArriveTimeM(minb+90,time));
        items.add(new ArriveTimeM(minb+105,time));
        items.add(new ArriveTimeM(minb+120,time));
        items.add(new ArriveTimeM(minb+135,time));
        items.add(new ArriveTimeM(minb+150,time));
        items.add(new ArriveTimeM(minb+165,time));
        items.add(new ArriveTimeM(minb+180,time));
        items.add(new ArriveTimeM(minb+195,time));
        items.add(new ArriveTimeM(minb+210,time));
        items.add(new ArriveTimeM(minb+225,time));
        items.add(new ArriveTimeM(minb+140,time));
        items.add(new ArriveTimeM(minb+255,time));
        items.add(new ArriveTimeM(minb+270,time));
        items.add(new ArriveTimeM(minb+285,time));
        items.add(new ArriveTimeM(minb+300,time));
        final SelectSendTimeLV adapter = new SelectSendTimeLV(items,activity);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EventBus.getDefault().post(new SelectArriveTimeEvent(adapter.getItem(position)));
                dismiss();
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
