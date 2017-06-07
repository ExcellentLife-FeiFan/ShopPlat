package com.ytxd.spp.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ytxd.spp.R;
import com.ytxd.spp.ui.views.InGridView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by XY on 2016/11/4.
 */

public class GoodCommentItemVH extends BaseViewHolder {
    public View rootView;
    public CircleImageView civ;
    public TextView tvName;
    public TextView tvTime;
    public TextView tvContent;
    public InGridView gvPic;


    public GoodCommentItemVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.civ = (CircleImageView) rootView.findViewById(R.id.civ);
        this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        this.tvTime = (TextView) rootView.findViewById(R.id.tv_time);
        this.tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        this.gvPic = (InGridView) rootView.findViewById(R.id.gv_pic);

    }
}
