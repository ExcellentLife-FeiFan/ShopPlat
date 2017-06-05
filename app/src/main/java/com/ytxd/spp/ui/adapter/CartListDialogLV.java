package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcxtzhang.lib.AnimShopButton;
import com.ytxd.spp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class CartListDialogLV extends CommonListAdapter<String> {
    public CartListDialogLV(List<String> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cart_dialog_good, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_origin_p)
        TextView tvOriginP;
        @BindView(R.id.btnAdd)
        AnimShopButton btnAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
