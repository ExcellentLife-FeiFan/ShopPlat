package com.ytxd.spp.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.model.MerchantM;
import com.ytxd.spp.ui.adapter.viewholder.HomeMerchantItemVH;
import com.ytxd.spp.util.ImageLoadUtil;

import java.util.List;


public class HomeMerchantA extends BaseQuickAdapter<MerchantM, HomeMerchantItemVH> {
    public HomeMerchantA(List<MerchantM> data) {
        super(R.layout.item_home_merchant, data);
    }

    protected HomeMerchantItemVH createBaseViewHolder(View view) {
        return new HomeMerchantItemVH(view);
    }


    @Override
    protected void convert(final HomeMerchantItemVH helper, MerchantM item) {
        helper.tvName.setText(item.getName());
        helper.tvQisongP.setText("¥" + item.getQSPrice() + "起送");
        helper.tvDistriP.setText("配送费¥" + item.getQSPrice());
        ImageLoadUtil.setImageNP(item.getLogoUrl(), helper.icon, mContext);
        List<MerchantM.ManJianBean> actis = item.getManJian();
        helper.tvActiNum.setText(actis.size() + "个活动");
        ViewGroup header = (ViewGroup) helper.expandActi.getHeaderRelativeLayout().findViewById(R.id.ll_ex_header);
        ViewGroup content = (ViewGroup) helper.expandActi.getContentRelativeLayout().findViewById(R.id.ll_ex_content);
        header.removeAllViews();
        content.removeAllViews();
        if (actis.size() > 0 && actis.size() <= 2) {
            for (int i = 0; i < actis.size(); i++) {
                TextView tvActi = (TextView) mLayoutInflater.inflate(R.layout.item_merchant_activity, null);
                tvActi.setText(actis.get(i).getManJianName());
                header.addView(tvActi);
            }

        } else if (actis.size() > 2) {
            List<MerchantM.ManJianBean> one = actis.subList(0, 2);
            List<MerchantM.ManJianBean> two = actis.subList(2, actis.size());
            for (int i = 0; i < one.size(); i++) {
                TextView tvActi = (TextView) mLayoutInflater.inflate(R.layout.item_merchant_activity, null);
                tvActi.setText(one.get(i).getManJianName());
                header.addView(tvActi);
            }

            for (int i = 0; i < two.size(); i++) {
                TextView tvActi = (TextView) mLayoutInflater.inflate(R.layout.item_merchant_activity, null);
                tvActi.setText(two.get(i).getManJianName());
                content.addView(tvActi);
            }

        }

        helper.llActiExpandIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.expandv.switchState(true);
                if (helper.expandActi.isOpened()) {
                    helper.expandActi.hide();
                } else {
                    helper.expandActi.show();
                }
            }
        });

    }

}
