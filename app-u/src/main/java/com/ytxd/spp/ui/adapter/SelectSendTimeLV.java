package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.ArriveTimeM;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class SelectSendTimeLV extends CommonListAdapter<ArriveTimeM> {
    public SelectSendTimeLV(List<ArriveTimeM> items, Activity activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_send_time, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            CommonUtils.setText(viewHolder.tvTime, "尽快送达  " +item.getTimeDesrcDay2()+ item.getTimeDesrcHour());
        } else {
            CommonUtils.setText(viewHolder.tvTime, item.getTimeDesrcDay2()+item.getTimeDesrcHour());
        }
        CommonUtils.setText(viewHolder.tvMinusAfter, item.getMinutesAfter() + "分钟后");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_minus_after)
        TextView tvMinusAfter;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
