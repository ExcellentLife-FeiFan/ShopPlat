package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class OrderSubGoodsLV extends CommonListAdapter<ShoppingCartM.Goods> {
    public OrderSubGoodsLV(List<ShoppingCartM.Goods> items, Activity activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_order_sub_good, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommonUtils.setText(viewHolder.tvName, item.getGoodM().getGoodsTitle());
        CommonUtils.setText(viewHolder.tvNum, "x" + item.getCount());
        CommonUtils.setText(viewHolder.tvOriginP, "¥ " + item.getGoodM().getYPrice());
        CommonUtils.setText(viewHolder.tvCurrentP, "¥" + item.getGoodM().getXPrice());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_num)
        TextView tvNum;
        @BindView(R.id.tv_origin_p)
        TextView tvOriginP;
        @BindView(R.id.tv_current_p)
        TextView tvCurrentP;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
