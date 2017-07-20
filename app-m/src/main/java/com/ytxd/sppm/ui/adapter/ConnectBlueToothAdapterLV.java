package com.ytxd.sppm.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.sppm.R;
import com.ytxd.sppm.util.AbStrUtil;
import com.ytxd.sppm.util.PrintUtils;
import com.ytxd.sppm.util.SPUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class ConnectBlueToothAdapterLV extends CommonListAdapter<BluetoothDevice> {

    public ConnectBlueToothAdapterLV(List<BluetoothDevice> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_bluetooth_adapter, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCuurent.setVisibility(View.GONE);
        String cB= SPUtil.getInstance().getString("bluetoothaddress");
        if (!AbStrUtil.isEmpty(cB)) {
            if (cB.equals(item.getAddress())) {
                viewHolder.tvCuurent.setVisibility(View.VISIBLE);
                if(null==PrintUtils.bluetoothDevice){
                    PrintUtils.bluetoothDevice=item;
                }
            }
        }
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvAddress.setText(item.getAddress());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_cuurent)
        TextView tvCuurent;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
