package com.ytxd.spp.view;

import com.ytxd.spp.model.OrderGoodM;

import java.util.List;

/**
 * 主界面的接口
 * Created by panl on 15/12/22.
 */
public interface IAddCommentView extends IBaseView {

    void lodeGoodsSuccess(List<OrderGoodM> items);

    void commentSuccess();

    void showDialog(String cotent);

    void dissmisDialog();

    void uploadIMGSuccess(String url);

    void uploadIMGFail();

}
