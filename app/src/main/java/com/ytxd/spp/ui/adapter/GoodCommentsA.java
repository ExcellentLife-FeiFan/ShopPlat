package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.ui.adapter.viewholder.GoodCommentItemVH;

import java.util.List;


public class GoodCommentsA extends BaseQuickAdapter<String, GoodCommentItemVH> {
    public GoodCommentsA(List<String> data) {
        super(R.layout.item_good_comment, data);
    }

    protected GoodCommentItemVH createBaseViewHolder(View view) {
        return new GoodCommentItemVH(view);
    }


    @Override
    protected void convert(GoodCommentItemVH helper, String item) {


    }

}
