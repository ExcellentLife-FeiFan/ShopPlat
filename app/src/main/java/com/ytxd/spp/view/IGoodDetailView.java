package com.ytxd.spp.view;

import com.ytxd.spp.model.GoodEvaluateM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IGoodDetailView extends IBaseView {

    void lodeCommentSuccess(List<GoodEvaluateM> items);

    void lodeCommentFailed();
}
