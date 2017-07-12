package com.ytxd.spp.view;

import com.ytxd.spp.model.MerchantEvaluateM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IMCommentView extends IBaseView {

    void lodeSuccess(List<MerchantEvaluateM> items);

    void lodeFailed();
}
