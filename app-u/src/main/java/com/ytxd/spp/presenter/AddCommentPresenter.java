package com.ytxd.spp.presenter;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.GetRequest;
import com.ytxd.spp.base.App;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.net.ApiResult;
import com.ytxd.spp.net.Apis;
import com.ytxd.spp.net.JsonCallback;
import com.ytxd.spp.util.AbStrUtil;
import com.ytxd.spp.util.LogUtils;
import com.ytxd.spp.util.ToastUtil;
import com.ytxd.spp.view.IAddCommentView;

import java.io.File;
import java.util.List;

/**
 * 主界面presenter
 * Created by panl on 15/12/24.
 */
public class AddCommentPresenter extends BasePresenter<IAddCommentView> {

    public AddCommentPresenter(Context context, IAddCommentView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
    }

    public void getGoodsInfo(String OrderCode) {
        OkGo.<ApiResult<List<OrderGoodM>>>get(Apis.GetOrderGoodsInfo)//
                .params("OrderCode", OrderCode)
                .execute(new JsonCallback<ApiResult<List<OrderGoodM>>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<List<OrderGoodM>>> response) {
                        ApiResult<List<OrderGoodM>> result = response.body();
                        if (result.isSuccess()) {
                            iView.lodeGoodsSuccess(result.getObj());
                        } else {
                            iView.lodeGoodsSuccess(null);
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<List<OrderGoodM>>> response) {
                        iView.lodeGoodsSuccess(null);
                        super.onError(response);
                    }
                });

    }


    public void addComment(String content, List<OrderGoodM> goods, int distrS, int goodS, String imgUrl, String orderCode, String SupermarketCode) {
        GetRequest getRequest = OkGo.<ApiResult<Object>>get(Apis.AddEvaluate)//
                .params("UserCode", App.user.getUserCode())
                .params("OrderCode", orderCode)
                .params("SupermarketCode", SupermarketCode)
                .params("EvaluateContent", content)
                .params("PSStar", distrS)
                .params("GoodsStar", goodS);

        if (!AbStrUtil.isEmpty(imgUrl)) {
            getRequest.params("PicPath", imgUrl);
        }
        String gs = "";
        for (int i = 0; i < goods.size(); i++) {
            gs = gs + goods.get(i).getGoodsCode();
            if (!AbStrUtil.isEmpty(goods.get(i).getComment())) {
                gs = gs + "$$$$" + goods.get(i).getComment();
            }else{
                gs = gs + "$$$$";
            }
            if (i == goods.size() - 1) {
                gs = gs + "$$$$" + goods.get(i).getZan();
            } else {
                gs = gs + "$$$$" + goods.get(i).getZan() + "$fg$";
            }
        }
        iView.showDialog("提交评论.....");
        getRequest.params("GoodsEvaluateInfo", gs)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialog();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.commentSuccess();
                        } else {
                            ToastUtil.showToastShort(context, "评论失败：" + result.getMsg());
                        }
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.dissmisDialog();
                        ToastUtil.showToastShort(context, "评论失败");
                        super.onError(response);
                    }
                });


    }

    public void upLoadImg(List<File> files) {
        iView.showDialog("上传图片中.....");
        OkGo.<ApiResult<Object>>post(Apis.UpFiles)//
                .addFileParams("files", files)
                .execute(new JsonCallback<ApiResult<Object>>() {
                    @Override
                    public void onSuccess(Response<ApiResult<Object>> response) {
                        iView.dissmisDialog();
                        ApiResult<Object> result = response.body();
                        if (result.isSuccess()) {
                            iView.uploadIMGSuccess((String) result.getObj());
                        } else {
                            iView.uploadIMGFail();
                            ToastUtil.showToastShort(context, result.getMsg());
                        }
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        LogUtils.e(progress.currentSize + "/" + progress.totalSize);
                        super.uploadProgress(progress);
                    }

                    @Override
                    public void onError(Response<ApiResult<Object>> response) {
                        iView.uploadIMGFail();
                        iView.dissmisDialog();
                        super.onError(response);
                    }
                });

    }
}
