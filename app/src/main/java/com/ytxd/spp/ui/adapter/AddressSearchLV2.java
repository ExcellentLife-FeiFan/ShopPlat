package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.ytxd.spp.R;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class AddressSearchLV2 extends CommonListAdapter<PoiItem> {
    public AddressSearchLV2(List<PoiItem> items, Activity activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_address_search, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.tv_current.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tv_current.setVisibility(View.GONE);
        }
        CommonUtils.setText(viewHolder.tvTitle, item.getTitle());
        CommonUtils.setText(viewHolder.tvDesr, item.getCityName()+item.getAdName()+item.getSnippet());

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desr)
        TextView tvDesr;
        @BindView(R.id.tv_current)
        TextView tv_current;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
