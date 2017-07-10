package com.ytxd.spp.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.GoodEvaluateM;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by apple on 2017/4/18.
 */

public class GoodCommentLV extends CommonListAdapter<GoodEvaluateM> {

    public GoodCommentLV(List<GoodEvaluateM> items, Context activity) {
        super(items, activity);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_good_comment, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CommonUtils.setText(viewHolder.tvName, item.getNickName());
        CommonUtils.setText(viewHolder.tvTime, CommonUtils.getFormatTimeString(item.getCreateTime()));
        if (item.getZOrC() == 1) {
            viewHolder.tvZan.setText("满意");
        } else {
            viewHolder.tvZan.setText("不满意");
        }
        CommonUtils.setText(viewHolder.tvContent, item.getEvaluateContent());
        ImageLoadUtil.setImageNP2(item.getTitlePath(), viewHolder.civ, context);

        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.civ)
        CircleImageView civ;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_zan)
        TextView tvZan;
        @BindView(R.id.tv_content)
        TextView tvContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
