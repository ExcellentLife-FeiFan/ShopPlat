package com.ytxd.spp.ui.adapter.viewholder;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.ninegrid.NineGridView;
import com.ytxd.spp.R;

import de.hdodenhof.circleimageview.CircleImageView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by XY on 2016/11/4.
 */

public class MerchantCommentVH extends BaseViewHolder {
    public View rootView;
    public CircleImageView civ;
    public TextView tvName;
    public TextView tvTime;
    public MaterialRatingBar rbDistr;
    public MaterialRatingBar rbGood;
    public TextView tvContent;
    public NineGridView gv;


    public MerchantCommentVH(View rootView) {
        super(rootView);
        this.rootView = rootView;
        this.civ = (CircleImageView) rootView.findViewById(R.id.civ);
        this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        this.tvTime = (TextView) rootView.findViewById(R.id.tv_time);
        this.rbDistr = (MaterialRatingBar) rootView.findViewById(R.id.rb_distr);
        this.rbGood = (MaterialRatingBar) rootView.findViewById(R.id.rb_good);
        this.tvContent = (TextView) rootView.findViewById(R.id.tv_content);
        this.gv = (NineGridView) rootView.findViewById(R.id.gv);

    }


}
