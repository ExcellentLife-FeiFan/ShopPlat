package com.ytxd.spp.ui.adapter;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ytxd.spp.R;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.ui.views.MutilRadioGroup;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by apple on 2017/4/18.
 */

public class AddCommGoodsLV extends CommonListAdapter<OrderGoodM> {
    public AddCommGoodsLV(List<OrderGoodM> items, Activity activity) {
        super(items, activity);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        final ViewHolder viewHolder;
//        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_add_comment_good, null);
            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
        CommonUtils.setText(viewHolder.tvName, item.getGoodsTitle());
        viewHolder.mrgZan.setOnCheckedChangeListener(new MutilRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MutilRadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_zan) {
                    CommonUtils.setImageDrawable(viewHolder.ivZan, R.drawable.bg_pricecolor_border_halfcircle);
                    CommonUtils.setImageDrawable(viewHolder.ivCai, R.drawable.bg_gray_border_halfcircle);
                    getItem(position).setZan(1);
                } else {
                    CommonUtils.setImageDrawable(viewHolder.ivCai, R.drawable.bg_pricecolor_border_halfcircle);
                    CommonUtils.setImageDrawable(viewHolder.ivZan, R.drawable.bg_gray_border_halfcircle);
                    getItem(position).setZan(2);
                }
            }
        });
        viewHolder.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getItem(position).setComment(s.toString());
                LogUtils.e();
            }
        });
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_zan)
        ImageView ivZan;
        @BindView(R.id.rb_zan)
        RadioButton rbZan;
        @BindView(R.id.rl_zan)
        RelativeLayout rlZan;
        @BindView(R.id.iv_cai)
        ImageView ivCai;
        @BindView(R.id.rb_cai)
        RadioButton rbCai;
        @BindView(R.id.rl_cai)
        RelativeLayout rlCai;
        @BindView(R.id.mrg_zan)
        MutilRadioGroup mrgZan;
        @BindView(R.id.et)
        EditText et;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
