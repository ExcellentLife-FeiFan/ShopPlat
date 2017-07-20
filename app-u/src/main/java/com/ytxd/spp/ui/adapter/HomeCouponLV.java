package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.util.AbDateUtil;
import com.ytxd.spp.util.CommonUtils;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class HomeCouponLV extends CommonListAdapter<CouponM> {
    public HomeCouponLV(List<CouponM> items, Activity activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder helper;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_counpon_small, null);
            helper = new ViewHolder(convertView);
            convertView.setTag(helper);
        } else {
            helper = (ViewHolder) convertView.getTag();
        }

        String b = CommonUtils.getFormatTimeString(item.getUseBeginTime());
        String e = CommonUtils.getFormatTimeString(item.getUseEndTime());
        CommonUtils.setText(helper.tvPrice, item.getCouponCode());
        CommonUtils.setText(helper.tvTitle, item.getCouponName());
        CommonUtils.setText(helper.tvDesrc, "满" + item.getMMoneyUse() + "元可用");
        CommonUtils.setText(helper.tvUsedTime,
                "使用时间："
                        + AbDateUtil.getStringByFormat(b, AbDateUtil.dateFormatYMD2)
                        + " ~ "
                        + AbDateUtil.getStringByFormat(e, AbDateUtil.dateFormatYMD2));
        long now = new Date().getTime();
        long b1 = AbDateUtil.getDateByFormat(b, AbDateUtil.dateFormatYMDHMS).getTime();
        long e1 = AbDateUtil.getDateByFormat(e, AbDateUtil.dateFormatYMDHMS).getTime();

        if (CommonUtils.getBoolean(item.getIsUse())) {
            CommonUtils.setBackgroundDrawable(helper.llBg, R.drawable.coupon_used);
            CommonUtils.setTextColor(helper.tvTitle, R.color.gray);
            CommonUtils.setTextColor(helper.tvDesrc, R.color.hint_gray);
            CommonUtils.setTextColor(helper.tvUsedTime, R.color.hint_gray);
            helper.ivClose.setVisibility(View.VISIBLE);
        } else {
            if (now >= b1 && now <= e1) {
                CommonUtils.setBackgroundDrawable(helper.llBg, R.drawable.coupon_u);
                CommonUtils.setTextColor(helper.tvTitle, R.color.black);
                CommonUtils.setTextColor(helper.tvDesrc, R.color.gray);
                CommonUtils.setTextColor(helper.tvUsedTime, R.color.colorPrimary);
                helper.ivClose.setVisibility(View.GONE);
                item.setPast(false);
            } else {
                CommonUtils.setBackgroundDrawable(helper.llBg, R.drawable.coupon_nu);
                CommonUtils.setTextColor(helper.tvTitle, R.color.gray);
                CommonUtils.setTextColor(helper.tvDesrc, R.color.hint_gray);
                CommonUtils.setTextColor(helper.tvUsedTime, R.color.hint_gray);
                helper.ivClose.setVisibility(View.VISIBLE);
                item.setPast(true);
            }
        }

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_desrc)
        TextView tvDesrc;
        @BindView(R.id.tv_used_time)
        TextView tvUsedTime;
        @BindView(R.id.ll_bg)
        LinearLayout llBg;
        @BindView(R.id.iv_close)
        ImageView ivClose;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
