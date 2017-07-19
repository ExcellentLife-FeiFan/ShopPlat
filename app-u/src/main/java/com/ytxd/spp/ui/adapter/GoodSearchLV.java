package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.ytxd.spp.R;
import com.ytxd.spp.event.GoodAddEvent;
import com.ytxd.spp.event.GoodMinusEvent;
import com.ytxd.spp.event.MerchantSelectGoodStandEvent;
import com.ytxd.spp.model.GoodM;
import com.ytxd.spp.ui.views.AnimShopButton;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.util.ShoppingCartUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;


/**
 * Created by apple on 2017/4/18.
 */

public class GoodSearchLV extends CommonListAdapter<GoodM> {
    String merchantCode;
    public GoodSearchLV(List<GoodM> items, Context activity, String merchantCode) {
        super(items, activity);
        this.merchantCode=merchantCode;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_mer_good, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
   /*     holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(mContext, GoodDetailActivity.class);
                itent.putExtra("data", items.get(sectionIndex).getChildren().get(itemIndex));
                itent.putExtra("merchantCode", merchantM.getSupermarketCode());
                itent.putExtra("merchant", merchantM);
                mContext.startActivity(itent);
            }
        });*/
        CommonUtils.setText(holder.tvName, item.getGoodsTitle());
        holder.tvMonthSales.setText("已售" + item.getSaleNumber() + "份");
        CommonUtils.setText(holder.tvOriginP, item.getYPrice());
        CommonUtils.setText(holder.tvNowP, item.getXPrice());
        ImageLoadUtil.setImageNP(item.getLogoPaths(), holder.iv, context, 0.6f);
        if (null != item.getGoods() && item.getGoods().size() > 0) {
            holder.rlAddBtn.setVisibility(View.INVISIBLE);
            holder.tvSelectStand.setVisibility(View.VISIBLE);
        } else {
            holder.rlAddBtn.setVisibility(View.VISIBLE);
            holder.tvSelectStand.setVisibility(View.INVISIBLE);
        }
        holder.btnAdd.setOnAddDelListener(new AnimShopButton.IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                EventBus.getDefault().post(new GoodAddEvent(holder.ivPlus, items.get(position), 1));
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                EventBus.getDefault().post(new GoodMinusEvent(items.get(position), 1));
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });
        holder.tvSelectStand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MerchantSelectGoodStandEvent(items.get(position)));
            }
        });
        holder.btnAdd.setCount(ShoppingCartUtil.getLocalCartGoodCount(item.getGoodsCode(), merchantCode));
        holder.btnAdd.invalidate();


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv)
        RoundedImageView iv;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_month_sales)
        TextView tvMonthSales;
        @BindView(R.id.tv_now_p)
        TextView tvNowP;
        @BindView(R.id.tv_origin_p)
        TextView tvOriginP;
        @BindView(R.id.ll_p)
        LinearLayout llP;
        @BindView(R.id.iv_plus)
        ImageView ivPlus;
        @BindView(R.id.btnAdd)
        AnimShopButton btnAdd;
        @BindView(R.id.rl_add_btn)
        RelativeLayout rlAddBtn;
        @BindView(R.id.tv_select_stand)
        TextView tvSelectStand;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
