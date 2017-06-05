package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class AddressManaLV extends CommonListAdapter<String> {
    public AddressManaLV(List<String> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_address_mana, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_poeple_name)
        TextView tvPoepleName;
        @BindView(R.id.tv_poeple_sex)
        TextView tvPoepleSex;
        @BindView(R.id.tv_poeple_phone)
        TextView tvPoeplePhone;
        @BindView(R.id.tv_address_type)
        TextView tvAddressType;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_delete)
        TextView tvDelete;
        @BindView(R.id.tv_edit)
        TextView tvEdit;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
