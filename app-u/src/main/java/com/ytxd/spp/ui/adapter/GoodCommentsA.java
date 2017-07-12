package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.GoodEvaluateM;
import com.ytxd.spp.ui.adapter.viewholder.GoodCommentItemVH;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;


public class GoodCommentsA extends BaseQuickAdapter<GoodEvaluateM, GoodCommentItemVH> {
    public GoodCommentsA(List<GoodEvaluateM> data) {
        super(R.layout.item_good_comment, data);
    }

    protected GoodCommentItemVH createBaseViewHolder(View view) {
        return new GoodCommentItemVH(view);
    }


    @Override
    protected void convert(GoodCommentItemVH viewHolder, GoodEvaluateM item) {
        CommonUtils.setText(viewHolder.tvName, item.getNickName());
        CommonUtils.setText(viewHolder.tvTime, CommonUtils.getFormatTimeString(item.getCreateTime()));
        if (item.getZOrC() == 1) {
            viewHolder.tvZan.setText("满意");
        } else {
            viewHolder.tvZan.setText("不满意");
        }
        CommonUtils.setText(viewHolder.tvContent, item.getEvaluateContent());
        ImageLoadUtil.setImageNP2(item.getTitlePath(), viewHolder.civ, mContext);
    }

}
