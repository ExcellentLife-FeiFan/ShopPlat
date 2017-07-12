package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class SelectACEAdLV extends CommonListAdapter<AddressM> {
    public SelectACEAdLV(List<AddressM> items, Activity activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_search_ad_ace, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommonUtils.setText(viewHolder.tvName,item.getContacts());
        CommonUtils.setText(viewHolder.tvPhone,item.getPhone());
        CommonUtils.setText(viewHolder.tvAddress,item.getAddressDescribe());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_address_t)
        TextView tvAddressT;
        @BindView(R.id.tv_address)
        TextView tvAddress;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
