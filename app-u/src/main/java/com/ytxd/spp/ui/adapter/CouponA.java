package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.CouponM;
import com.ytxd.spp.ui.adapter.viewholder.CouponVH;
import com.ytxd.spp.util.AbDateUtil;
import com.ytxd.spp.util.CommonUtils;

import java.util.Date;
import java.util.List;


public class CouponA extends BaseQuickAdapter<CouponM, CouponVH> {
    public CouponA(List<CouponM> data) {
        super(R.layout.item_counpon, data);
    }

    protected CouponVH createBaseViewHolder(View view) {
        return new CouponVH(view);
    }


    @Override
    protected void convert(CouponVH helper, CouponM item) {
        String b = CommonUtils.getFormatTimeString(item.getUseBeginTime());
        String e = CommonUtils.getFormatTimeString(item.getUseEndTime());
        CommonUtils.setText(helper.tvPrice, item.getCouponCode());
        CommonUtils.setText(helper.tvTitle, item.getCouponName());
        CommonUtils.setText(helper.tvDesrc, "满" + item.getMMoneyUse() + "元可用");
        CommonUtils.setText(helper.tvUsedTime,
                "使用时间："
                        + AbDateUtil.getStringByFormat(b, AbDateUtil.dateFormatYMD2)
                        + "-"
                        + AbDateUtil.getStringByFormat(e, AbDateUtil.dateFormatYMD2));
        long now = new Date().getTime();
        long b1 = AbDateUtil.getDateByFormat(b, AbDateUtil.dateFormatYMDHMS).getTime();
        long e1 = AbDateUtil.getDateByFormat(e, AbDateUtil.dateFormatYMDHMS).getTime();

        if (CommonUtils.getBoolean(item.getIsUse())) {
            CommonUtils.setBackgroundDrawable(helper.llBg, R.drawable.coupon_used);
            CommonUtils.setTextColor(helper.tvTitle, R.color.gray);
            CommonUtils.setTextColor(helper.tvDesrc, R.color.hint_gray);
            CommonUtils.setTextColor(helper.tvUsedTime, R.color.hint_gray);
            helper.ivClosed.setVisibility(View.VISIBLE);
        } else {
            if (now >= b1 && now <= e1) {
                CommonUtils.setBackgroundDrawable(helper.llBg, R.drawable.coupon_u);
                CommonUtils.setTextColor(helper.tvTitle, R.color.black);
                CommonUtils.setTextColor(helper.tvDesrc, R.color.gray);
                CommonUtils.setTextColor(helper.tvUsedTime, R.color.colorPrimary);
                helper.ivClosed.setVisibility(View.GONE);
                item.setPast(false);
            } else {
                CommonUtils.setBackgroundDrawable(helper.llBg, R.drawable.coupon_nu);
                CommonUtils.setTextColor(helper.tvTitle, R.color.gray);
                CommonUtils.setTextColor(helper.tvDesrc, R.color.hint_gray);
                CommonUtils.setTextColor(helper.tvUsedTime, R.color.hint_gray);
                helper.ivClosed.setVisibility(View.VISIBLE);
                item.setPast(true);
            }
        }
        CommonUtils.setText(helper.tv_remark, item.getGetContent());
    }

}
