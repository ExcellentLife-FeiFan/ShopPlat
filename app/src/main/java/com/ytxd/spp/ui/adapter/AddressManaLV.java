package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.AddressM;
import com.ytxd.spp.ui.activity.mine.account.AddOrEditAddressActivity;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class AddressManaLV extends CommonListAdapter<AddressM> {
    public AddressManaLV(List<AddressM> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_address_mana, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (item.isIsDefault()) {
            viewHolder.tvAddressType.setVisibility(View.VISIBLE);
            viewHolder.tvSetDefault.setVisibility(View.GONE);
        } else {
            viewHolder.tvAddressType.setVisibility(View.GONE);
            viewHolder.tvSetDefault.setVisibility(View.VISIBLE);
        }
        viewHolder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteAD(getItem(position).getSHAddressCode());

            }
        });
        viewHolder.tvSetDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setDefault(getItem(position).getSHAddressCode());

            }
        });

        viewHolder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddOrEditAddressActivity.class);
                intent.putExtra("data", getItem(position));
                context.startActivity(intent);
            }
        });
        CommonUtils.setText(viewHolder.tvAddress, item.getAddressDescribe());
        CommonUtils.setText(viewHolder.tvPoepleName, item.getContacts());
        CommonUtils.setText(viewHolder.tvPoepleSex, item.getSex() == 1 ? "（先生）" : "（女士）");
        CommonUtils.setText(viewHolder.tvPoeplePhone, item.getPhone());
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
        @BindView(R.id.tv_setdefault)
        TextView tvSetDefault;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
