package com.ytxd.spp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.ytxd.spp.R;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.ui.adapter.viewholder.MerchantCommentVH;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;


public class MerchantCommentA extends BaseQuickAdapter<String, MerchantCommentVH> {
    public MerchantCommentA(List<String> data) {
        super(R.layout.item_merchant_comment, data);
    }

    protected MerchantCommentVH createBaseViewHolder(View view) {
        return new MerchantCommentVH(view);
    }


    @Override
    protected void convert(final MerchantCommentVH helper, String item) {
        String img="/UpFiles/2017/7/4/201706231344540.jpg,/UpFiles/2017/7/4/Screenshot_20170704-061152.png,/UpFiles/2017/7/4/Screenshot_20170704-061152.png";
        String[] photoStr = null;
        if (!AbStrUtil.isEmpty(img)) {
            photoStr = img.split(",");
        }
        if (photoStr != null) {
            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            List<String> urls = CommonUtils.getStringList(photoStr);
            for (String url : urls) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(Apis.AddPATH2(url));
                info.setBigImageUrl(Apis.AddPATH2(url));
                imageInfo.add(info);
            }
            helper.gv.setVisibility(View.VISIBLE);
            helper.gv.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
         /*   if (urls != null && urls.size() == 1) {
                helper.gv.setSingleImageRatio(urls.get(0).width * 1.0f / images.get(0).height);
            }*/
        } else {
            helper.gv.setVisibility(View.GONE);
        }
    }

}
