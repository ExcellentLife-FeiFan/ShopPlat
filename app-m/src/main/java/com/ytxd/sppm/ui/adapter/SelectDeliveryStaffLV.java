package com.ytxd.sppm.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.sppm.R;
import com.ytxd.sppm.model.DeliveryStaffM;
import com.ytxd.sppm.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class SelectDeliveryStaffLV extends CommonListAdapter<DeliveryStaffM> {

    int positionS = -1;

    public SelectDeliveryStaffLV(List<DeliveryStaffM> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_delivery_staff, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (this.positionS == position) {
            CommonUtils.setBackgroundDrawable(viewHolder.tvName, R.color.price_color);
            CommonUtils.setTextColor(viewHolder.tvName, R.color.white);
        } else {
            CommonUtils.setBackgroundDrawable(viewHolder.tvName, R.color.white);
            CommonUtils.setTextColor(viewHolder.tvName, R.color.black_overlay2);
        }
        viewHolder.tvName.setText(item.getDeliveryStaffName());
        return convertView;
    }


    public void setPositionS(int positionS) {
        this.positionS = positionS;
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
