package com.ytxd.spp.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.ytxd.spp.R;
import com.ytxd.spp.base.AppManager;
import com.ytxd.spp.base.BaseActivity;
import com.ytxd.spp.model.OrderGoodM;
import com.ytxd.spp.model.OrderM;
import com.ytxd.spp.presenter.AddCommentPresenter;
import com.ytxd.spp.ui.adapter.AddCommGoodsLV;
import com.ytxd.spp.ui.adapter.PicsSelectA;
import com.ytxd.spp.ui.views.InListView;
import com.ytxd.spp.ui.views.RecyclerItemClickListener;
import com.ytxd.spp.util.CommonUtils;
import com.ytxd.spp.util.DialogUtils;
import com.ytxd.spp.util.HideUtil;
import com.ytxd.spp.util.ImageLoadUtil;
import com.ytxd.spp.view.IAddCommentView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class AddCommentActivity extends BaseActivity<AddCommentPresenter> implements View.OnClickListener, IAddCommentView {


    @BindView(R.id.civ)
    CircleImageView civ;
    @BindView(R.id.tv_mer_name)
    TextView tvMerName;
    @BindView(R.id.rb_dist)
    MaterialRatingBar rbDist;
    @BindView(R.id.rb_good)
    MaterialRatingBar rbGood;
    @BindView(R.id.rv_pics)
    RecyclerView rv_pics;
    @BindView(R.id.et)
    EditText et;
    /*    @BindView(R.id.gv_pic)
        InGridView gvPic;*/
    AddCommGoodsLV goodsLV;
    @BindView(R.id.lv_goods)
    InListView lvGoods;
    @BindView(R.id.msv)
    MultiStateView msv;
    private PicsSelectA photoAdapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private OrderM orderM;
    private String imgUrl;

    @Override
    protected void initPresenter() {
        presenter = new AddCommentPresenter(activity, this);
        presenter.init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcomment);
        ButterKnife.bind(this);
        HideUtil.init(this);
        getBar().initActionBar("添加评论", this);
        orderM = (OrderM) getIntent().getSerializableExtra("data");
        CommonUtils.setText(tvMerName, orderM.getSuperMarketModel().getName());
        ImageLoadUtil.setImageNP(orderM.getSuperMarketModel().getLogoUrl(), civ, this);
        goodsLV = new AddCommGoodsLV(new ArrayList<OrderGoodM>(), this);
        lvGoods.setAdapter(goodsLV);
        photoAdapter = new PicsSelectA(this, selectedPhotos);
        rv_pics.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        rv_pics.setAdapter(photoAdapter);
        rv_pics.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PicsSelectA.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(4)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(activity);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(activity);
                        }
                    }
                }));
        presenter.getGoodsInfo(orderM.getOrderCode());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                AppManager.getInstance().killActivity(this);
                break;
        }

    }

    @OnClick({R.id.ll_shop, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_shop:
                break;
            case R.id.btn_add:
                if (rbDist.getProgress() == 0) {
                    showToast("请给配送评分");
                } else if (rbGood.getProgress() == 0) {
                    showToast("请给商品评分");
                } else {
                    if (selectedPhotos.size() > 0) {
                        List<File> files = new ArrayList<>();
                        for (int i = 0; i < selectedPhotos.size(); i++) {
                            files.add(new File(selectedPhotos.get(i)));
                        }
                        presenter.upLoadImg(files);
                    } else {
                        addComment("");
                    }
                }
                break;
        }
    }

    private void addComment(String imgUrl) {
        presenter.addComment(et.getText().toString()
                , goodsLV.getItems()
                , rbDist.getProgress()
                , rbGood.getProgress()
                , imgUrl
                , orderM.getOrderCode()
                , orderM.getSuperMarketModel().getSupermarketCode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void lodeGoodsSuccess(List<OrderGoodM> items) {
        if (null != items) {
            goodsLV.addItems(items, true);
        }
        msv.setViewState(MultiStateView.VIEW_STATE_CONTENT);
    }

    @Override
    public void commentSuccess() {
        showToast("评价成功");
        AppManager.getInstance().killActivity(this);
    }

    @Override
    public void showDialog(String cotent) {
        DialogUtils.showDialog(this, cotent);
    }

    @Override
    public void dissmisDialog() {
        DialogUtils.dismissDialog();
    }

    @Override
    public void uploadIMGSuccess(String url) {
        addComment(url);
    }

    @Override
    public void uploadIMGFail() {

    }
}
