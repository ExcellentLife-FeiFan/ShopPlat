package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class TagOrderRemarkLV extends CommonListAdapter<String> {
    public List<String> selects = new ArrayList<>();

    public TagOrderRemarkLV(List<String> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tag_order_remark, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (this.selects.contains(item)) {
            CommonUtils.setTextColor(viewHolder.tvName, R.color.price_color);
            CommonUtils.setBackgroundDrawable(viewHolder.tvName, R.drawable.bg_pricecolor_border);
        } else {
            CommonUtils.setTextColor(viewHolder.tvName, R.color.gray);
            CommonUtils.setBackgroundDrawable(viewHolder.tvName, R.drawable.bg_graycolor_border);
        }
        viewHolder.tvName.setText(item);
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setSelect(int positionS, String item) {
        if (this.selects.contains(item)) {
            this.selects.remove(item);
            notifyDataSetChanged();
        } else {
            this.selects.add(item);
            notifyDataSetChanged();

        }
    }
}
