package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;
import com.ytxd.spp.R;
import com.ytxd.spp.event.MerchantGoodAddEvent;
import com.ytxd.spp.event.MerchantGoodMinusEvent;
import com.ytxd.spp.model.ShoppingCartM;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by apple on 2017/4/18.
 */

public class CartListDialogLV extends CommonListAdapter<ShoppingCartM.Goods> {
    public CartListDialogLV(List<ShoppingCartM.Goods> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cart_dialog_good, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageLoadUtil.setImageNP(item.getGoodM().getLogoPaths(), viewHolder.iv, context);
        CommonUtils.setText(viewHolder.tvName, item.getGoodM().getGoodsTitle());
        CommonUtils.setText(viewHolder.tvOriginP, "¥ " + item.getGoodM().getYPrice());
        CommonUtils.setText(viewHolder.tvNowP, item.getGoodM().getXPrice());
        viewHolder.btnAdd.setCount(item.getCount());
        viewHolder.btnAdd.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                EventBus.getDefault().post(new MerchantGoodAddEvent(items.get(position).getGoodM()));
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                EventBus.getDefault().post(new MerchantGoodMinusEvent(items.get(position).getGoodM()));
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_now_p)
        TextView tvNowP;
        @BindView(R.id.tv_origin_p)
        TextView tvOriginP;
        @BindView(R.id.btnAdd)
        AnimShopButton btnAdd;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
