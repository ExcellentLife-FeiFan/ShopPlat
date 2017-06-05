package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class GoodStandLV extends CommonListAdapter<String> {
    public int positionS = 0;

    public GoodStandLV(List<String> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_good_stand, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (this.positionS == position) {
            CommonUtils.setTextColor(viewHolder.tvName, R.color.white);
            CommonUtils.setBackgroundDrawable(viewHolder.tvName, R.drawable.bg_pricecolor_halfcicle);
        } else {
            CommonUtils.setTextColor(viewHolder.tvName, R.color.gray);
            CommonUtils.setBackgroundDrawable(viewHolder.tvName, R.drawable.bg_gray_border_halfcircle);
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

    public void setPositionS(int positionS) {
        this.positionS = positionS;
        notifyDataSetChanged();
    }
}
